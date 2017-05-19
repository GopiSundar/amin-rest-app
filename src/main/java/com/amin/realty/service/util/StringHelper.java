
package com.amin.realty.service.util;


import java.util.UUID;
import java.util.regex.Pattern;


public final class StringHelper {



	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * Default empty {@link String} value (<code>""</code>).
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * Default empty {@link UUID} {@link String} value (<code>"00000000-0000-0000-0000-000000000000"</code>).
	 */
	public static final String EMPTY_GUID = "00000000-0000-0000-0000-000000000000";

	/**
	 * The Regular Expression pattern used to determine if a string matches a
	 * serialized GUID. A {@link String} representation of a {@link UUID}
	 * may occur in one of the following formats ('d' represents a hexadecimal
	 * digit whose case is ignored):
	 * <ul>
	 *   <li>
	 *     32 contiguous digits:<br />
	 *     {@code dddddddddddddddddddddddddddddddd}
	 *   </li>
	 *   <li>
	 *     32 digits in groups of 8, 4, 4, 4, and 12 digits with hyphens
	 *     between the groups:<br />
	 *     {@code dddddddd-dddd-dddd-dddd-dddddddddddd}
	 *   </li>
	 *   <li>
	 *     32 digits in groups of 8, 4, 4, 4, and 12 digits with hyphens
	 *     between the groups, with parenthesis around the whole thing:<br />
	 *     {@code (dddddddd-dddd-dddd-dddd-dddddddddddd)}
	 *   </li>
	 *   <li>
	 *     Four hexadecimal values enclosed in braces, where the fourth value is
	 *     a subset of eight hexadecimal values that is also enclosed in braces:<br />
	 *     <code>{0xdddddddd, 0xdddd, 0xdddd,{0xdd,0xdd,0xdd,0xdd,0xdd,0xdd,0xdd,0xdd}}</code>
	 *   </li>
	 * </ul>
	 * <p />
	 * <p />
	 * Hint: Use <a href="http://www.regexplanet.com/advanced/java/index.html">http://www.regexplanet.com/advanced/java/index.html</a>
	 * to validate/test java regex patterns easily.
	 */
	public static final String rxGuidPattern = "^[0-9a-fA-F]{32}$|^(\\{|\\()?([0-9a-fA-F]{8}-)([0-9a-fA-F]{4}-){3}([0-9a-fA-F]{12})(\\}|\\))?$|^(\\{)?[0x0-9a-fA-F]{3,10}(, {0,1}[0x0-9a-fA-F]{3,6}){2}, {0,1}(\\{)([0x0-9a-fA-F]{3,4}, {0,1}){7}[0x0-9a-fA-F]{3,4}(\\}\\})$";
/*
	'  1 & 2:                       "^(\{){0,1}[0-9a-fA-F]{8}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{12}(\}){0,1}$"
	'  official spec: 1, 2 & 3:     "^({|\()?[A-Fa-f0-9]{8}-([A-Fa-f0-9]{4}-){3}[A-Fa-f0-9]{12}(}|\))?$"
	'  0, 1 & 2:                    "^((?-i:0x)?[A-Fa-f0-9]{32}|[A-Fa-f0-9]{8}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{12}|\{[A-Fa-f0-9]{8}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{12}\})$"
	'  0, 1, 2 & 3:                 "^[A-Fa-f0-9]{32}$|^({|\()?[A-Fa-f0-9]{8}-([A-Fa-f0-9]{4}-){3}[A-Fa-f0-9]{12}(}|\))?$"
	'  0, 1, 2 & 3:                 "^[0-9a-fA-F]{32}$|^({|\()?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}(}|\))?$"
	'  0, 1, 2, 3 & 4:              "^[A-Fa-f0-9]{32}$|({|\()?[A-Fa-f0-9]{8}-([A-Fa-f0-9]{4}-){3}[A-Fa-f0-9]{12}(}|\))?$|^({)?[0xA-Fa-f0-9]{3,10}(, {0,1}[0xA-Fa-f0-9]{3,6}){2}, {0,1}({)([0xA-Fa-f0-9]{3,4}, {0,1}){7}[0xA-Fa-f0-9]{3,4}(}})$"
	'  0, 1, 2, 3 & 4:              "^[0-9a-fA-F]{32}$|({|\()?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}(}|\))?$|^({)?[0x0-9a-fA-F]{3,10}(, {0,1}[0x0-9a-fA-F]{3,6}){2}, {0,1}({)([0x0-9a-fA-F]{3,4}, {0,1}){7}[0x0-9a-fA-F]{3,4}(}})$"
	'32 contiguous digits: dddddddddddddddddddddddddddddddd         (0)
	'-or- Groups of 8, 4, 4, 4, and 12 digits with hyphens between the groups. The entire GUID can optionally be enclosed in matching braces or parentheses:
	'                      dddddddd-dddd-dddd-dddd-dddddddddddd     (1)
	'                      {dddddddd-dddd-dddd-dddd-dddddddddddd}   (2)
	'                      (dddddddd-dddd-dddd-dddd-dddddddddddd)   (3)
	'-or- Groups of 8, 4, and 4 digits, and a subset of eight groups of 2 digits, with each group prefixed by "0x" or "0X", and separated by commas. The entire GUID, as well as the subset, is enclosed in matching braces:
	'                      {0xdddddddd, 0xdddd, 0xdddd,{0xdd,0xdd,0xdd,0xdd,0xdd,0xdd,0xdd,0xdd}}   (4)
*/
	public static final Pattern rxGuid = Pattern.compile( rxGuidPattern, Pattern.CASE_INSENSITIVE );
	//   Return If(instance.IsNullOrEmpty, Guid.Empty, _
	//     If(rxGuid.IsMatch(instance), New Guid(instance), Guid.Empty))




	// Suppress default constructor for non-instantiability:
	private StringHelper() {
		throw new AssertionError();
	}




	/**
	 * Similar to {@link String#contains(CharSequence)} except that it ignores
	 * case.
	 * <p>
	 *   A {@code null} in either parameter will result in {@code false} being
	 *   returned.
	 * </p>
	 *
	 * @param 	src
	 * 		The {@link String } to search within.
	 * @param 	what
	 * 		The {@link String } to search for.
	 *
	 * @return
	 * 		{@code true} if the {@code src} contains {@code what};
	 * 		{@code false} otherwise.
	 */
	public static boolean containsIgnoreCase(
	  final String src,
	  final String what
	) {

		boolean retVal = false;

		if ( ( src != null ) && ( what != null ) ) {

			final int length = what.length();

			// If empty string is contained:
			if ( length == 0 ) {

				retVal = true;

			} else {

				final char firstLo = Character.toLowerCase( what.charAt(0) );
				final char firstUp = Character.toUpperCase( what.charAt(0) );

				for ( int x = 0; x < src.length(); x++ ) {

					// Quick check before calling more expensive regionMatches method:
					final char ch = src.charAt( x );
					if ( ( ch != firstLo ) && ( ch != firstUp ) ) {

						continue;

					} else if ( src.regionMatches( true, x, what, 0, length ) ) {

						// Match found, set flag and break:
						retVal = true;
						break;

					}

				}

			}

		}

		return retVal;

	} // containsIgnoreCase(String,String)




	/**
	 * Returns the given string if it is non-empty; null otherwise.
	 *
	 * @param	instance
	 * 		The string to test and possibly return.
	 *
	 * @return
	 * 		{@code instance} if it is non-empty; null if it is
	 * 		{@link #EMPTY_STRING} or null.
	 */
	public static   String convertEmptyToNull(
	    final String instance
	) {

		return ( isNullOrEmpty( instance ) ? null : instance );

	} // convertEmptyToNull(String)




	/**
	 * Returns the given string if it is non-null; the empty string otherwise.
	 *
	 * @param	instance
	 * 		The string to test and possibly return.
	 *
	 * @return
	 * 		{@code instance} if it is non-null; {@link #EMPTY_STRING} otherwise.
	 */
	public static String convertNullToEmpty(
	    final String instance
	) {

		return ( ( instance == null ) ? EMPTY_STRING : instance );

	} // convertNullToEmpty(String)




	/**
	 * Converts the specified {@code instance} from a {@link String}
	 * representation of a {@link UUID} to the equivalent {@link UUID} value.
	 *
	 * @param	instance
	 * 		A {@link String} representation of a {@link UUID} to convert.
	 *
	 * @return
	 * 		The {@link UUID} equivalent of the {@link String} value
	 * 		{@code instance}.
	 * 		<p>-or-</p>
	 * 		{@link GuidHelper#Empty} if {@code instance} is null or not a
	 * 		{@link String} representation of a {@link UUID}.
	 */
	public static UUID convertToGuid(
	    final String instance
	) {

		String guidToConvert;
		int length;
		UUID retVal;
		String testGuid;

		guidToConvert = EMPTY_GUID;
		if ( isGuid( instance ) ) {

			testGuid = ( instance.indexOf(" ") >= 0 )
			  ? instance.replaceAll( " ", "" )
			  : instance;
			length = testGuid.length();
			switch ( length ) {

				case 32: // "dddddddddddddddddddddddddddddddd"
					guidToConvert = new StringBuilder( 36 )
					  .append( testGuid.substring( 0, 8 ) ).append("-")
					  .append( testGuid.substring( 8, 12 ) ).append("-")
					  .append( testGuid.substring( 12, 16 ) ).append("-")
					  .append( testGuid.substring( 16, 20 ) ).append("-")
					  .append( testGuid.substring( 20 ) ).toString();
					break;

				case 38: // "{dddddddd-dddd-dddd-dddd-dddddddddddd}"
					final String start = testGuid.substring( 0, 1 );
					final String end = testGuid.substring( length - 1 );
					if ( ( "{".equals( start ) || "(".equals( start ) )
					  && ( "}".equals( end ) || ")".equals( end ) )
					  && ( "-".equals( testGuid.substring( 9, 10 ) ) )
					  && ( "-".equals( testGuid.substring( 14, 15 ) ) )
					  && ( "-".equals( testGuid.substring( 19, 20 ) ) )
					  && ( "-".equals( testGuid.substring( 24, 25 ) ) )
					) {
						//"(ca761232-ed42-11ce-bacd-00aa0057b223)"
						//  0        1    1    2    2           3
						//  1        0    5    0    5           7
						guidToConvert = testGuid.substring( 1, length - 1 );
					}
					break;

				case 68: // "{0xdddddddd,0xdddd,0xdddd,{0xdd,0xdd,0xdd,0xdd,0xdd,0xdd,0xdd,0xdd}}"
					if ( ( "{".equals( testGuid.substring( 0, 1 ) ) && "{".equals( testGuid.substring( 26, 27 ) ) && "}".equals( testGuid.substring( length - 1 ) ) && "}".equals( testGuid.substring( length - 2, length - 1 ) ) )
					  && ( "0x".equals( testGuid.substring( 1, 3 ) ) && "0x".equals( testGuid.substring( 12, 14 ) ) && "0x".equals( testGuid.substring( 19, 21 ) ) && "0x".equals( testGuid.substring( 27, 29 ) ) && "0x".equals( testGuid.substring( 32, 34 ) ) && "0x".equals( testGuid.substring( 37, 39 ) ) && "0x".equals( testGuid.substring( 42, 44 ) ) && "0x".equals( testGuid.substring( 47, 49 ) ) && "0x".equals( testGuid.substring( 52, 54 ) ) && "0x".equals( testGuid.substring( 57, 59 ) ) && "0x".equals( testGuid.substring( 62, 64 ) ) )
					  && ( ",".equals( testGuid.substring( 11, 12 ) ) && ",".equals( testGuid.substring( 18, 19 ) ) && ",".equals( testGuid.substring( 25, 26 ) ) && ",".equals( testGuid.substring( 31, 32 ) ) && ",".equals( testGuid.substring( 36, 37 ) ) && ",".equals( testGuid.substring( 46, 47 ) ) && ",".equals( testGuid.substring( 51, 52 ) ) && ",".equals( testGuid.substring( 56, 57 ) ) && ",".equals( testGuid.substring( 61, 62 ) ) )
					) {
						//"{0xCA761232,0xED42,0x11CE,{0xBA,0xCD,0x00,0xAA,0x00,0x57,0xB2,0x23}}"
						//    0          1      2       2    3    3    4    4    5    5    6
						//    3          4      1       9    4    9    4    9    4    9    4
						//"ca761232-ed42-11ce-bacd-00aa0057b223"
						guidToConvert = new StringBuilder( 36 )
						  .append( testGuid.substring( 3, 11 ) ).append("-")
						  .append( testGuid.substring( 14, 18 ) ).append("-")
						  .append( testGuid.substring( 21, 25 ) ).append("-")
						  .append( testGuid.substring( 29, 31 ) )
						  .append( testGuid.substring( 34, 36 ) ).append("-")
						  .append( testGuid.substring( 39, 41 ) )
						  .append( testGuid.substring( 44, 46 ) )
						  .append( testGuid.substring( 49, 51 ) )
						  .append( testGuid.substring( 54, 56 ) )
						  .append( testGuid.substring( 59, 61 ) )
						  .append( testGuid.substring( 64, 66 ) ).toString();
					}
					break;

				default:
					guidToConvert = testGuid;
					break;

			} // switch ( length )

		} // if ( isGuid( instance ) )

		retVal = UUID.fromString( guidToConvert );

		return retVal;

	} // convertToGuid(String)




	/**
	 * Returns {@code true} if the given string is <u>NOT</u> null or the empty
	 * string.
	 * <p />
	 * Consider normalizing strings with {@link #convertEmptyToNull} to use a
	 * simple equality check {@code instance == null} in place of this method.
	 *
	 * @param	instance
	 * 		A string to check for value (i.e. <u>NOT</u> null or empty).
	 *
	 * @return
	 * 		{@code true} if {@code instance} is <u>NOT</u> null or
	 * 		{@link #EMPTY_STRING}, {@code false} otherwise.
	 */
	public static boolean hasValue(
	    final String instance
	) {

		return ( ! ( ( instance == null ) || instance.isEmpty() ) );

	} // hasValue(String)




	/**
	 * Helper method to determine if a given string is a valid GUID string.
	 *
	 * @param	instance
	 * 		{@link String} to validate.
	 *
	 * @return
	 * 		{@code true} if is a valid GUID string, {@code false} otherwise.
	 */
	public static boolean isGuid(
	    final String instance
	) {

		return ( instance == null )
		  ? false
		  : rxGuid.matcher( instance ).matches();

	} // isGuid(String)




	/**
	 * Returns {@code true} if the given string is null or the empty string.
	 * <p />
	 * Consider normalizing strings with {@link #convertNullToEmpty} to use
	 * {@link String#isEmpty} in place of this method.
	 *
	 * @param	instance
	 * 		A string to check for null or empty.
	 *
	 * @return
	 * 		{@code true} if {@code instance} is null or {@link #EMPTY_STRING},
	 * 		{@code false} otherwise.
	 */
	public static boolean isNullOrEmpty(
	    final String instance
	) {

		return ( ( instance == null ) || instance.isEmpty() );

	} // isNullOrEmpty(String)




	/**
	 * Repeats the given {@link String} n times (performant).
	 *
	 * @param	theString
	 * 		The {@link String} to repeat.
	 * @param	n
	 * 		The repetition count.
	 *
	 * @return
	 * 		The given {@link String} repeated n times.
	 *
	 * @throws	NullPointerException
	 * 		If {@code theString} is null.
	 * @throws	IllegalArgumentException
	 * 		When the given repetition count is smaller than zero.
	 */
	public static String repeat(
	  final String theString,
	  final int n
	) {

		/*// Validate preconditions:
		checkNotNull( theString, "theString to repeat must not be null" );
		checkArgument( n >= 0, "the given repetition count must not be smaller than zero" );*/

		if ( n == 0 ) {
			return EMPTY_STRING;
		} else if ( n == 1 ) {
			return theString;
		} else if ( ( n % 2 ) == 0 ) {
			final String s = repeat( theString, n / 2 );
			return s.concat( s );
		} else {
			return theString.concat( repeat( theString, n - 1 ) );
		}

	} // repeat(String,int)




	/**
	 * Similar to {@link String#trim()} except this will remove the specified
	 * character from both ends (the prefix and suffix).
	 *
	 * @param	theString
	 * 		The string to be trimmed.
	 * @param	charToTrim
	 *		The character(s) to be removed.<br/>
	 *		If not provided, white-spaces are removed instead.
	 *
	 * @return
	 * 		{@code null}, if {@code str} is null, otherwise a string will be
	 * 		returned minus the character from both ends (prefix/suffix).
	 */
	public static String trim(
	  final String theString,
	  final char... charToTrim
	) {

		int counter;
		int prefixPos;
		String retVal;
		int startingLength;
		char[] strAsArray;
		int suffixPos;

		retVal = null;
		if ( theString != null ) {

			counter        = 0;
			startingLength = theString.length();
			suffixPos      = theString.length();
			prefixPos      = 0;
			strAsArray     = theString.toCharArray();

			if ( charToTrim.length > 0 ) {

				while ( ( prefixPos < suffixPos ) && ( counter < charToTrim.length ) ) {

					for ( counter = 0; counter < charToTrim.length; counter++ ) {

						if ( strAsArray[prefixPos] == charToTrim[counter] ) {
							prefixPos++;
							break;
						}
					}
				}

				counter = 0;
				while ( ( prefixPos < suffixPos ) && ( counter < charToTrim.length ) ) {

					for ( counter = 0; counter < charToTrim.length; counter++ ) {

						if ( strAsArray[suffixPos - 1] == charToTrim[counter] ) {
							suffixPos--;
							break;
						}
					}
				}

				retVal = ( ( prefixPos > 0 ) || ( suffixPos < startingLength ) )
				  ? theString.substring( prefixPos, suffixPos )
				  : theString;

			} else {
				retVal = theString.trim();
			}

		} // if ( theString != null )

		return retVal;

	} // trim(String,char...)




} // class StringHelper
