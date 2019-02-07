# aml-comments
Extension of the [2017 AgreementMakerLight](https://github.com/AgreementMakerLight/AML-Project) ontology alignment system that considers entity comments.

This variant of AML is intended to be used in situations where more than one target entity may be related to a single entity in the source ontology.

To leverage comments in the matching process, we modified the AML Lexicon to store comments in addition to entity labels. We then created a CommentMatcher class. The match method in this class iterates through all of the comments in the source ontology and identifies entities in the target ontology whose names are mentioned in the comment. Relationship strength is based on the number of words in common between the comment and the entity name, divided by the number of words in the comment. 

Other changes that were made include:

Changed the GUI code to allow thresholds as low as 0.0

Modified those matchers that only add a relation between x and y to the alignment if neither x nor y are already involved in a mapping with higher similarity. The string and neighborhood similarity matchers were the only ones that fell into this category. For the string matcher, blocks beginning at lines 111, 174, 186 and 213 were commented out, while for the neighborhood similarity matcher the comments began at lines 85 and 98. 

Changed ManualMatcher.java so that all matches are always added, when hierarchical mode is in use, rather than limiting the alignment to 1-to-1 (lines 85, 97, 108, and 117)

Commented out the filtering and repair functionality in ManualMatcher.java (lines 123-157)

Added a method called saveAll to Alignment.java that allows us to save AML output in the format needed for the evaluation of complex correspondences and made this available through the GUI by modifying AMLMenuBar.java at line 36 and AlignmentFileChooser.java

