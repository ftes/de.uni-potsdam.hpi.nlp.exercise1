package de.ftes.hpi.nlp.exercise1;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.ftes.hpi.nlp.languagemodel.BaseLineModel;
import de.ftes.hpi.nlp.languagemodel.BigramHiddenMarkovModel;
import de.ftes.hpi.nlp.languagemodel.Corpus;
import de.ftes.hpi.nlp.languagemodel.PartOfSpeechTagger;
import de.ftes.hpi.nlp.languagemodel.ViterbiAlgorithm;
import de.ftes.hpi.nlp.measures.PartOfSpeechTaggingPrecision;
import de.ftes.hpi.nlp.util.CorpusParser;
import de.ftes.hpi.nlp.util.TrainingAndTestCorpus;

public class Task2 {
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, URISyntaxException {
		Corpus corpus = CorpusParser.parse(new File(Task1.class.getResource("/GENIA_treebank_v1").toURI()), false);
		
		TrainingAndTestCorpus split = new TrainingAndTestCorpus(corpus, 0.9);
		PartOfSpeechTagger tagger = new BaseLineModel(split.trainingSet());
		
		// base line model
		double precision = PartOfSpeechTaggingPrecision.calc(tagger, split.testSet());
		System.out.println("BaseLine Model: " + precision);
		
		// hidden markov model (bigrams) with Viterbi alg
		BigramHiddenMarkovModel modelOnTrainingSet = new BigramHiddenMarkovModel(split.trainingSet());
		tagger = new ViterbiAlgorithm(modelOnTrainingSet);
		precision = PartOfSpeechTaggingPrecision.calc(tagger, split.testSet());
		System.out.println("Hidden Markov Model + Viterbi alg: " + precision);
		
	}
}
