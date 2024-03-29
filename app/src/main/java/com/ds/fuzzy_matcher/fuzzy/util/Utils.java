package com.ds.fuzzy_matcher.fuzzy.util;



//import com.ds.fuzzy_test.fuzzy.exception.MatchException;
//
//import org.apache.lucene.analysis.ngram.NGramTokenizer;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
//
//import java.io.IOException;
//import java.io.StringReader;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Utils {

    public static Stream<Object> getNGrams(Object value, int size) {
//off        Stream.Builder<Object> stringStream = Stream.builder();
//off        if (value.toString().length() <= size) {
//off            stringStream.add(value);
//off        } else {
//off
//off            NGramTokenizer nGramTokenizer = new NGramTokenizer(size, size);
//off            CharTermAttribute charTermAttribute = nGramTokenizer.addAttribute(CharTermAttribute.class);
//off            nGramTokenizer.setReader(new StringReader(value.toString()));
//off            try {
//off                nGramTokenizer.reset();
//off                while (nGramTokenizer.incrementToken()) {
//off                    stringStream.add(charTermAttribute.toString());
//off                }
//off                nGramTokenizer.end();
//off                nGramTokenizer.close();
//off            } catch (IOException io) {
//off                throw new MatchException("Failure in creating tokens : ", io);
//off            }
//off        }
//off        return stringStream.build();




        Stream.Builder<Object> stringStream = Stream.builder();
        String str = value.toString();
        int length = str.length();

        if (length <= size) {
            stringStream.add(value);

        } else {

            int limit = length - size;
            for(int i = 0; i <= limit; i++){
                String s = str.substring(i, i + size);
                stringStream.add(s);
            }
        }
        return stringStream.build();
    }

    /**
     * utility method to apply dictionary for normalizing strings
     *
     * @param str A String of element value to be nomalized
     * @param dict A dictonary map containing the mapping of string to normalize
     * @return the normalized string
     */
    public static String getNormalizedString(String str, Map<String, String> dict) {
        return Arrays.stream(str.split("\\s+"))
                .map(d -> dict.containsKey(d.toLowerCase()) ?
                        dict.get(d.toLowerCase())
                        : d)
                .collect(Collectors.joining(" "));
    }
}
