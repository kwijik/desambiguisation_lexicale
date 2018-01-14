package org.wic.wsd.corpus;

import java.io.FileNotFoundException;

import org.wic.wsd.dictionary.Dictionary;
import org.wic.wsd.dictionary.POS;
import org.wic.wsd.dictionary.Word;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

public class CorpusParser implements ContentHandler {

    private Text currentText;
    private Dictionary dictionary;
    Corpus corpus;
    boolean isInInstance;

    /**
     * Constructeur par defaut. 
     * @throws FileNotFoundException 
     */
    public CorpusParser(Corpus c, Dictionary d) throws FileNotFoundException {
	super();
	isInInstance = false;
	corpus = c;
	dictionary = d;
	// On definit le locator par defaut.
	locator = new LocatorImpl();
    }

    /**
     * Definition du locator qui permet a tout moment pendant l'analyse, de localiser
     * le traitement dans le flux. Le locator par defaut indique, par exemple, le numero
     * de ligne et le numero de caractere sur la ligne.
     * @author smeric
     * @param value le locator a utiliser.
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public void setDocumentLocator(Locator value) {
	locator =  value;
    }

    /**
     * Evenement envoye au demarrage du parse du flux xml.
     * @throws SAXException en cas de probleme quelquonque ne permettant pas de
     * se lancer dans l'analyse du document.
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException {
    }

    /**
     * Evenement envoye a la fin de l'analyse du flux xml.
     * @throws SAXException en cas de probleme quelquonque ne permettant pas de
     * considerer l'analyse du document comme etant complete.
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException {
    }

    /**
     * Debut de traitement dans un espace de nommage.
     * @param prefixe utilise pour cet espace de nommage dans cette partie de l'arborescence.
     * @param URI de l'espace de nommage.
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    public void startPrefixMapping(String prefix, String URI) throws SAXException {

    }

    /**
     * Fin de traitement de l'espace de nommage.
     * @param prefixe le prefixe choisi a l'ouverture du traitement de l'espace nommage.
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    public void endPrefixMapping(String prefix) throws SAXException {

    }

    /**
     * Evenement recu a chaque fois que l'analyseur rencontre une balise xml ouvrante.
     * @param nameSpaceURI l'url de l'espace de nommage.
     * @param localName le nom local de la balise.
     * @param rawName nom de la balise en version 1.0 <code>nameSpaceURI + ":" + localName</code>
     * @throws SAXException si la balise ne correspond pas a ce qui est attendu,
     * comme par exemple non respect d'une dtd.
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributes) throws SAXException {
	if(localName.equals("corpus")){

	}else if(localName.equals("text")){
	    //numP=1;
	    this.currentText = new Text(attributes.getValue(0));
	}else if(localName.equals("sentence")){
	   // this.currentSentence = new Sentence(attributes.getValue(0), context.nbPossibilityMax);
	    //numP++;
	}else if(localName.equals("instance")){
	    isInInstance = true;
	    int pos = POS.NONE;
	    if(attributes.getValue(2).equals("n"))
		pos = POS.NOUN;
	    else if(attributes.getValue(2).equals("v"))
		pos = POS.VERB;
	    else if(attributes.getValue(2).equals("a"))
		pos = POS.ADJECTIVE;
	    else if(attributes.getValue(2).equals("r"))
		pos = POS.ADVERB;

	    Word w = new Word(attributes.getValue(1).toLowerCase(), pos);
	    w.setIdTask(attributes.getValue(0));
	    w.createPos(dictionary);
	    currentText.addWord(w);

	}
    }

    /**
     * Evenement recu a chaque fermeture de balise.
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
	if(localName == "corpus"){

	}else if(localName == "text"){
	    corpus.getTexts().add(currentText);
	}else if(localName == "sentence"){
	    //this.context.add(currentSentence);
	}else if(localName == "instance"){
	    isInInstance = false;
	}
    }

    /**
     * Evenement recu a chaque fois que l'analyseur rencontre des caracteres (entre
     * deux balises).
     * @param ch les caracteres proprement dits.
     * @param start le rang du premier caractere a traiter effectivement.
     * @param end le rang du dernier caractere a traiter effectivement
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int end) throws SAXException {

    }

    /**
     * Recu chaque fois que des caracteres d'espacement peuvent etre ignores au sens de
     * XML. C'est a dire que cet evenement est envoye pour plusieurs espaces se succedant,
     * les tabulations, et les retours chariot se succedants ainsi que toute combinaison de ces
     * trois types d'occurrence.
     * @param ch les caracteres proprement dits.
     * @param start le rang du premier caractere a traiter effectivement.
     * @param end le rang du dernier caractere a traiter effectivement
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(char[] ch, int start, int end) throws SAXException {
	//System.out.println("espaces inutiles rencontres : ..." + new String(ch, start, end) +  "...");
    }

    /**
     * Rencontre une instruction de fonctionnement.
     * @param target la cible de l'instruction de fonctionnement.
     * @param data les valeurs associees a cette cible. En general, elle se presente sous la forme 
     * d'une serie de paires nom/valeur.
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    public void processingInstruction(String target, String data) throws SAXException {
	//System.out.println("Instruction de fonctionnement : " + target);
	//System.out.println("  dont les arguments sont : " + data);
    }

    /**
     * Recu a chaque fois qu'une balise est evitee dans le traitement a cause d'un
     * probleme non bloque par le parser. Pour ma part je ne pense pas que vous
     * en ayez besoin dans vos traitements.
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    public void skippedEntity(String arg0) throws SAXException {
	// Je ne fais rien, ce qui se passe n'est pas franchement normal.
	// Pour eviter cet evenement, le mieux est quand meme de specifier une dtd pour vos
	// documents xml et de les faire valider par votre parser.              
    }

    @SuppressWarnings("unused")
    private Locator locator;
}
