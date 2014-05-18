package de.ftes.hpi.nlp.de.ftes.hpi.nlp.exercise1;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import de.ftes.hpi.nlp.languagemodel.Corpus;
import de.ftes.hpi.nlp.util.CorpusParser;

public class CorpusParserTest {

	@Test
	public void testParsing() throws URISyntaxException, SAXException, IOException, ParserConfigurationException {
		Corpus corpus = CorpusParser.parse(new File(this.getClass().getResource("/").toURI()), true);
		
		System.out.println(corpus.toString());
	}
}
