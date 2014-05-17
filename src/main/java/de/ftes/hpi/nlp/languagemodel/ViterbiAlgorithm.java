package de.ftes.hpi.nlp.languagemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.ftes.hpi.nlp.util.DefaultHashMap;
import de.ftes.hpi.nlp.util.DefaultHashMap.DefaultFactory;

/**
 * @see <a href="http://en.wikipedia.org/wiki/Viterbi_algorithm">Viterbi Algorithm Wikipedia</a>
 * @author fredrik
 */
public class ViterbiAlgorithm implements PartOfSpeechTagger {
	private final TagLanguageModel languageModel;
	private final Set<String> tagSet;
	
	public ViterbiAlgorithm(TagLanguageModel languageModel) {
		this.languageModel = languageModel;
		tagSet = languageModel.getCorpus().getTagSet();
	}
	
	private static void put(List<Map<String, Double>> matrix, int tokenPosition, String tag, double value) {
		if (matrix.size() <= tokenPosition) {
			matrix.add(new HashMap<String, Double>());
		}
		Map<String, Double> column = matrix.get(tokenPosition);
		column.put(tag, value);
	}
	
	private static DefaultHashMap<String, List<String>> createPath() {
		return new DefaultHashMap<>(new DefaultFactory<List<String>>() {
			@Override
			public List<String> getDefault() {
				return new ArrayList<>();
			}
		});
	}
	
	@Override
	public Iterable<Token> determineMostLikelyTags(Iterable<Token> sentence) {
		List<Map<String, Double>> matrix = new ArrayList<>();
		Map<String, List<String>> paths = createPath();
		Iterator<Token> tokenIter = sentence.iterator();
		
		// initialize base cases (t == 0)
		String firstToken = tokenIter.next().getText();
		for (String tag : tagSet) {
			double startProb = languageModel.beginningOfSentenceTagProbability(tag);
			double emitProb = languageModel.tokenProbability(firstToken, tag);
			put(matrix, 0, tag, Math.log(startProb * emitProb));
			paths.get(tag).add(tag);
		}
		
		// run Viterbi for t > 0
		int i = 0;
		while (tokenIter.hasNext()) {
			String token = tokenIter.next().getText();
			i++;
			
			Map<String, List<String>> newPaths = createPath();
			for (String tag : tagSet) {
				double maxProbLog = Double.NEGATIVE_INFINITY;
				String prevTagWithMaxProb = null;
				for (String prevTag : tagSet) {
					double prevProbLog = matrix.get(i-1).get(prevTag);
					double transProb = languageModel.tagTransitionProbability(prevTag, tag);
					double emitProb = languageModel.tokenProbability(token, tag);
					double overallProbLog = prevProbLog + Math.log(transProb * emitProb);
					if (overallProbLog > maxProbLog) {
						maxProbLog = overallProbLog;
						prevTagWithMaxProb = prevTag;
					}
				}
				put(matrix, i, tag, maxProbLog);
				List<String> extended = new ArrayList<>(paths.get(prevTagWithMaxProb));
				extended.add(tag);
				newPaths.put(tag, extended);
			}
			
			paths = newPaths;
		}
		
		double maxProbLog = Double.NEGATIVE_INFINITY;
		String lastTagForMaxState = null;
		for (String tag : tagSet) {
			double probLog = matrix.get(i).get(tag);
			if (probLog > maxProbLog) {
				maxProbLog = probLog;
				lastTagForMaxState = tag;
			}
		}
		
		List<String> path = paths.get(lastTagForMaxState);
		List<Token> result = new ArrayList<>();
		i = 0;
		for (Token oldToken : sentence) {
			Token newToken = new Token(path.get(i), oldToken.getText());
			result.add(newToken);
			i++;
		}
		return result;
	}
	
	
	/*
	def viterbi(obs, states, start_p, trans_p, emit_p):
	    V = [{}]
	    path = {}
	
	    # Initialize base cases (t == 0)
	    for y in states:
	        V[0][y] = start_p[y] * emit_p[y][obs[0]]
	        path[y] = [y]
	
	    # Run Viterbi for t > 0
	    for t in range(1, len(obs)):
	        V.append({})
	        newpath = {}
	
	        for y in states:
	            (prob, state) = max((V[t-1][y0] * trans_p[y0][y] * emit_p[y][obs[t]], y0) for y0 in states)
	            V[t][y] = prob
	            newpath[y] = path[state] + [y]
	
	        # Don't need to remember the old paths
	        path = newpath
	    n = 0           # if only one element is observed max is sought in the initialization values
	    if len(obs)!=1:
	        n = t
	    print_dptable(V)
	    (prob, state) = max((V[n][y], y) for y in states)
	    return (prob, path[state])
	
	# Don't study this, it just prints a table of the steps.
	def print_dptable(V):
	    s = "    " + " ".join(("%7d" % i) for i in range(len(V))) + "\n"
	    for y in V[0]:
	        s += "%.5s: " % y
	        s += " ".join("%.7s" % ("%f" % v[y]) for v in V)
	        s += "\n"
	    print(s)
	 */
}
