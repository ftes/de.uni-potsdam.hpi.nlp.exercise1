package de.ftes.hpi.nlp.exercise1;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.ftes.hpi.nlp.languagemodel.BigramMLEModel;
import de.ftes.hpi.nlp.languagemodel.Corpus;
import de.ftes.hpi.nlp.languagemodel.LanguageModel;
import de.ftes.hpi.nlp.measures.Perplexity;
import de.ftes.hpi.nlp.util.CorpusParser;
import de.ftes.hpi.nlp.util.TrainingAndTestCorpus;

public class Task1 {
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, URISyntaxException {
		Corpus corpus = CorpusParser.parse(new File(Task1.class.getResource("/GENIA_treebank_v1").toURI()));
		
		TrainingAndTestCorpus split = new TrainingAndTestCorpus(corpus, 0.9);
		LanguageModel modelOnTrainingSet = new BigramMLEModel(split.trainingSet());
		double perplexity = Perplexity.calc(modelOnTrainingSet, split.testSet());
		
		System.out.println(perplexity);
	}
}
