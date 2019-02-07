// @author Michelle Cheatham
package aml.match;

import java.util.ArrayList;
import java.util.Set;

import aml.AML;
import aml.ontology.Lexicon;


public class CommentMatcher implements PrimaryMatcher {

	public CommentMatcher() { }
	
	
	@Override
	public Alignment match(double thresh)
	{
		System.out.println("Running Comment Matcher");
		long time = System.currentTimeMillis()/1000;
		//Get the lexicons of the source and target Ontologies
		AML aml = AML.getInstance();
		Lexicon sLex = aml.getSource().getLexicon();
		Lexicon tLex = aml.getTarget().getLexicon();
		//And match them
		Alignment a = match(sLex,tLex,thresh,false);
		time = System.currentTimeMillis()/1000 - time;
		System.out.println("Finished in " + time + " seconds");
		return a;
	}
	
	
	protected Alignment match(Lexicon sLex, Lexicon tLex, double thresh, boolean internal) {
		
		Alignment maps = new Alignment(internal);
		
		Set<String> comments = sLex.getComments();
		Set<String> names = tLex.getNames(); // TODO get property names as well
		
		for (String comment: comments) {
			
			String[] commentTokens = comment.toLowerCase().split("[ ]");
			
			for (String name: names) {
				
				String[] nameTokens = name.toLowerCase().split("[ ]");
				
				int count = 0;
				for (String commentToken: commentTokens) {
					for (String nameToken: nameTokens) {
						if (commentToken.equals(nameToken)) {
							count++;
							break;
						}
					}
				}
				
				double strength = count / (float) commentTokens.length;
				
				if (strength >= thresh) {
					ArrayList<Integer> sourceItems = sLex.getEntitiesWithComment(comment);
					Set<Integer> targetItems = tLex.getClasses(name);
					
					for (Integer sourceItem: sourceItems) {
						for (Integer targetItem: targetItems) {
							maps.add(sourceItem, targetItem, strength);
						}
					}
				}
			}
		}
		
		// TODO do the same thing in the other direction??
			
		return maps;
	}
}