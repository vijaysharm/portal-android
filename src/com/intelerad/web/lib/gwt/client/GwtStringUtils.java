package com.intelerad.web.lib.gwt.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class intends to provide GWT-compatible string utilities similar to commons StringUtils. 
 */
public class GwtStringUtils
{
    public static final String EMPTY = "";
    
    /**
     * Removes control characters (char &lt;= 32) from both
     * ends of this String returning an empty String ("") if the String
     * is empty ("") after the trim or if it is null.
     * @param str the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if null input
     */
    public static String trimToEmpty( String str )
    {
        return str == null ? EMPTY : str.trim();
    }

    /**
     * Checks if a String is whitespace, empty ("") or null.
     * @param str the String to check, may be null
     * @return true if the String is null, empty or whitespace
     */
    public static boolean isBlank( String str )
    {
        return str == null ? true : str.trim().length() == 0;
    }

    /**
     * Compares two Strings, returning true if they are equal.
     * nulls are handled without exceptions. Two null references
     * are considered to be equal. The comparison is case sensitive.
     * @param str1  the first String, may be null
     * @param str2  the second String, may be null
     * @return true if the Strings are equal, case sensitive, or both null
     */
    public static boolean equals( String str1, String str2 )
    {
        return str1 == null ? str2 == null : str1.equals( str2 );
    }

    /**
     * Compares this @param str1 to @code param str2, ignoring case
     * considerations.  Two strings are considered equal ignoring case if they
     * are of the same length and corresponding characters in the two strings
     * are equal ignoring case.
     */
    public static boolean equalsIgnoreCase( String str1, String str2 )
    {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase( str2 );
    }

    /**
     * Null safe comparison for enums. 
     */
    public static boolean equals( Enum<?> e1, Enum<?> e2 )
    {
        return e1 == null ? e2 == null : e1.equals( e2 );
    }
    
    /** @return true if <code>type</code> is included in <code>types</code> or
     * if <code>types</code> is null. */
    public static boolean matchesAny( String type, String[] types )
    {
        if ( types == null )
            return true;
        
        for ( int idx = 0; idx < types.length; idx++ )
        {
            if ( type.equals( types[idx] ) )
                return true;
        }
        return false;
    }
    
    public static String arrayJoin( final Object input[],
                                    final String separator )
    {
        return arrayJoin( input, separator, 0, input.length );
    }

    public static String arrayJoin( final Object input[],
                                    final String separator,
                                    final int offset,
                                    final int count )
    {
        StringBuffer joined = new StringBuffer( count * 10 );
        int max = offset + count;
        for ( int i = offset; i < max; i++ )
        {
            joined.append( input[i] );
            if ( i < max - 1 )
                joined.append( separator );
        }
        return joined.toString();
    }
    
    public static String listJoin( final List<?> input, 
                                   final String separator )
    {
       return arrayJoin( input.toArray(), separator ); 
    }
    
    public static String listJoin( final List<?> input, 
                                   final String separator,
                                   final int offset,
                                   final int count )
    {
       return arrayJoin( input.toArray(), separator, offset, count ); 
    }
    
    public static String collectionJoin( final Collection<?> input, 
                                         final String separator )
    {
       return arrayJoin( input.toArray(), separator ); 
    }

    /*
     * Returns a string with all French accented characters converted
     * to their equivalent non-accented characters
     */
    public static String normalizeString( String s )
    {
        return s
                    // replace upper case characters
                    .replace( '\u00C0', 'A' )
                    .replace( '\u00C2', 'A' )
                    .replace( '\u00C4', 'A' )
                    
                    .replace( '\u00C9', 'E' )
                    .replace( '\u00C8', 'E' )
                    .replace( '\u00CA', 'E' )
                    .replace( '\u00CB', 'E' )
                    
                    .replace( '\u00CE', 'I' )
                    .replace( '\u00CF', 'I' )
                    
                    .replace( '\u00D2', 'O' )
                    .replace( '\u00D4', 'O' )
                    
                    .replace( '\u00D9', 'U' )
                    .replace( '\u00DB', 'U' )
                    .replace( '\u00DC', 'U' )
                    
                    .replace( '\u00C7', 'C' )
                    
                    // replace lower case characters
                    .replace( '\u00E0', 'a' )
                    .replace( '\u00E2', 'a' )
                    .replace( '\u00E4', 'a' )
                    
                    .replace( '\u00E9', 'e' )
                    .replace( '\u00E8', 'e' )
                    .replace( '\u00EA', 'e' )
                    .replace( '\u00EB', 'e' )
                    
                    .replace( '\u00EE', 'i' )
                    .replace( '\u00EF', 'i' )
                    
                    .replace( '\u00F2', 'o' )
                    .replace( '\u00F4', 'o' )
                    
                    .replace( '\u00F9', 'u' )
                    .replace( '\u00FB', 'u' )
                    .replace( '\u00FC', 'u' )
                    
                    .replace( '\u00E7', 'c' );
    }
    
    /**
     * Split 'input' on 'separator', stripping empty elements but not trimming whitespace.
     */
    public static List<String> split( final String input,
                                      final String separator )
    {
        return split( input, separator, true, false );
    }

    /**
     * Split 'input' on 'separator' without trimming whitespace, optionally stripping
     * empty elements.
     */
    public static List<String> split( final String input,
                                      final String separator,
                                      final boolean stripEmpty )
    {
        return split( input, separator, stripEmpty, false );
    }

    /**
     * Split 'input' on 'separator', optionally trimming whitespace and stripping
     * empty elements.  (Whitespace is trimmed *before* stripping empty elements.)
     */
    public static List<String> split( final String input,
                                      final String separator,
                                      final boolean stripEmpty,
                                      final boolean trim )
    {
        List<String> result = new ArrayList<String>();
        tokenize( input, separator, stripEmpty, trim, result );
        return result;
    }
    
    private static void tokenize( final String input,
                                  final String separator,
                                  final boolean stripEmpty,
                                  final boolean trim,
                                  Collection<String> target )
    {
        if ( input == null || ( input.trim() ).length() == 0 )
            return;
        if ( separator == null || separator.length() == 0 )
            throw new IllegalArgumentException( "split delimiter must not be empty or null" );

        int index = 0;
        int num;
        while ( ( num = input.indexOf( separator, index ) ) != -1 )
        {
            if ( index != num )
            {
                String val = input.substring( index, num );

                if ( trim )
                    val = val.trim();
                
                if ( val.length() > 0 || !stripEmpty )
                    target.add( val );
            }
            else if ( !stripEmpty )
            {
                target.add( "" );
            }
            index = num + separator.length();
        }
   
        num = input.length();
        if ( num >= index )
        {
            String val;
            if ( trim )
                val = input.substring( index, num ).trim();
            else
                val = input.substring( index, num );
            
            if ( val.length() > 0 || !stripEmpty )
                target.add( val );
        }
    } // tokenize

    /*
    * JavaScript function that performs a string comparison,
    * insensitive to accents, case, and apostrophes.
    * Returns:
    *   0 :     Strings exactly match
    *   -1 :    target < source
    *   1 :     target > source
    */
    public native static int localeCompare( String source, String target )
    /*-{
        return source.localeCompare( target );
    }-*/;
    
    /**
     * 
     * @return capitalized name, taking into account dashes ( for compound names like "Jean-Paul") 
     * and apostrophes (for names like "O'Regan")
     */
    public static String capitalizeName( String str ) 
    {
        return capitalizeFully( str, new char[]{ '\n', '\r', '\t', ' ', '\'', '-'  } );
    }
    
    /// TOTALLY RIPPED OFF ALL THESE CAPITALIZATION ROUTINES FROM APACHE WordUtils
    
    public static String capitalizeFully( String str ) 
    {
        return capitalizeFully( str, null );
    }
    
    public static String capitalizeFully( String str, char[] delimiters ) 
    {
        if (str == null || str.length() == 0) 
        {
            return str;
        }
        str = str.toLowerCase();
        return capitalize( str, delimiters );
    }
    
    public static String capitalize( String str, char[] delimiters ) 
    {
        if ( str == null || str.length() == 0 ) 
        {
            return str;
        }
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer( strLen );
        
        int delimitersLen = 0;
        if ( delimiters != null ) 
        {
            delimitersLen = delimiters.length;
        }
        
        boolean capitalizeNext = true;
        for ( int i = 0; i < strLen; i++ ) 
        {
            char ch = str.charAt( i );
        
            boolean isDelimiter = false;
            if ( delimiters == null ) 
            {
                // WordUtils uses Character.isWhitespace( ch ) here, but GWT doesn't support that
                // because of UNICODE issues
                isDelimiter = "\n\r\t ".indexOf( ch ) != -1;
            } 
            else 
            {
                for ( int j = 0; j < delimitersLen; j++ ) 
                {
                    if ( ch == delimiters[j] ) 
                    {
                        isDelimiter = true;
                        break;
                    }
                }
            }
        
            if ( isDelimiter ) 
            {
                buffer.append( ch );
                capitalizeNext = true;
            } 
            else if ( capitalizeNext ) 
            {
                // WordUtils uses Character.toTitleCase( ch ) here, but GWT doesn't support that  
                // because of UNICODE issues
                buffer.append( Character.toUpperCase( ch ) );
                capitalizeNext = false;
            }
            else 
            {
                buffer.append( ch );
            }
        }
        return buffer.toString();
    }
    /// END WordUtils
    
    // These constants as well as method escapeCharacters() were copied from StringEscape,
    // which is not GWT client friendly.
    public static final String UPPERCASE_ASCII = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWERCASE_ASCII = "abcdefghijklmnopqrstuvwxyz";

    /**
     * This was copied from StringEscape, which is not GWT client friendly.
     * 
     * Transform and escape characters in <code>input</code> and return a new string.
     *
     * Iterate over <code>input</code>, prefixing all characters that
     * occur in <code>mappedChars</code> with <code>escapeChar</code> and
     * replace them with corresponding characters from <code>replaceChars</code>.
     *
     * <code>mappedChars</code> and <code>replaceChars</code> must be non-null and 
     * of the same length.
     * 
     * @param input string to process
     * @param mappedChars characters that should be transformed and escaped
     * @param replaceChars characters to to replace mapped characters with
     * @param escapeChar leading escape character to use
     * @return transformed and escaped string
     */
    public static String escapeCharacters( final String input,
                                           final String mappedChars,
                                           final String replaceChars,
                                           final char escapeChar )
    {
        assert mappedChars.length() == replaceChars.length();
        
        if ( input == null )
            return input;
        
        final int length = input.length();
        StringBuilder output = new StringBuilder( length + length/2 );
        for ( int i = 0; i < length; i++ )
        {
            char curChar = input.charAt( i );
            int mapIndex = mappedChars.indexOf( curChar );
            if ( mapIndex != -1 )
            {
                output.append( escapeChar );
                output.append( replaceChars.charAt( mapIndex ) );
            }
            else if ( escapeChar == curChar )
            {
                output.append( escapeChar ); // Escape the escape character.
                output.append( escapeChar ); 
            }
            else
            {
                output.append( curChar );
            }
        }
        return output.toString();
    }
    
    static public boolean containsIgnoreCase( Iterable<String> source , String searchedString )
    {    
        for ( String sourceElement : source )
        {
            if ( sourceElement.equalsIgnoreCase( searchedString ) )
            {
                return true;                
            }
        }   
        return false;
    }

}
