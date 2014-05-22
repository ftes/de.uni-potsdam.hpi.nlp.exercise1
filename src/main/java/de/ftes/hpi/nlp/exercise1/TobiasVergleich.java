package de.ftes.hpi.nlp.exercise1;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.ftes.hpi.nlp.languagemodel.BigramMLEModel;
import de.ftes.hpi.nlp.languagemodel.Corpus;
import de.ftes.hpi.nlp.languagemodel.TokenLanguageModel;
import de.ftes.hpi.nlp.measures.Perplexity;
import de.ftes.hpi.nlp.util.CorpusParser;

public class TobiasVergleich {
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, URISyntaxException {
		Corpus training = CorpusParser.parse(new File(Task1.class.getResource("/tobiastest1").toURI()), false);
		Corpus test = CorpusParser.parse(new File(Task1.class.getResource("/tobiastest2").toURI()), false);
		System.out.println("sen " + training.getNumSentences());
		System.out.println("tok " + training.getNumTokens());
		System.out.println("voc " + training.getVocabularySize());
		
		TokenLanguageModel modelOnTrainingSet = new BigramMLEModel(training);
		System.out.println("P(squirrel at beginning) = " + modelOnTrainingSet.wordProbabilityAtBeginningOfSentence("squirrel"));
		System.out.println("P(monkeys after squirrel) = " + modelOnTrainingSet.wordProbabilitiyGivenPrevious("squirrel", "monkeys"));
		System.out.println("P(squirrel after monkeys) = " + modelOnTrainingSet.wordProbabilitiyGivenPrevious("monkeys", "squirrel"));
		System.out.println("P(we) = " + modelOnTrainingSet.wordProbabilityAtBeginningOfSentence("we"));
		System.out.println("P(we previously) = " + modelOnTrainingSet.wordProbabilitiyGivenPrevious("we", "previously"));
		System.out.println("P(previously reported) = " + modelOnTrainingSet.wordProbabilitiyGivenPrevious("previously", "reported"));
		System.out.println("P(that the) = " + modelOnTrainingSet.wordProbabilitiyGivenPrevious("that", "the"));
		System.out.println("P(cell specific) = " + modelOnTrainingSet.wordProbabilitiyGivenPrevious("cell", "specific"));
		double perplexity = Perplexity.calc(modelOnTrainingSet, test);
		System.out.println("P(sentence) " + Math.exp(modelOnTrainingSet.sentenceProbabilityLogarithm(test.getSentences().iterator().next())));
		
		System.out.println("Perplexity: " + perplexity);
		
//		System.out.println("Perplexity(log 3.521E-31) = " + Perplexity.calc(model, corpus));
	}
}
