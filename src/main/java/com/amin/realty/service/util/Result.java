package com.amin.realty.service.util;

import static com.amin.realty.service.util.StringHelper.EMPTY_STRING;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;


public final class Result implements Serializable {




	private static final long	serialVersionUID		= 1L;
	

	private int					code;
	private Object				data;
	private String				message;
	private boolean				result;
	



	/**
	 * Default constructor to initialize a new (empty) instance of the
	 * <b>Result</b> type.
	 */
	public Result() {

		this.code = 0;
		this.data = null;
		this.message = EMPTY_STRING;
		this.result = true;

	} // Result()




	/**
	 * Initializes a new instance of the <b>Result</b> type from the specified
	 * string {@code message}.
	 *
	 * @param	message
	 * 		A string containing the message to initialize this <b>Result</b> to.
	 */
	public Result( final String message ) {

		this.code = 0;
		this.data = null;
		this.message = message;
		this.result = false;

	} // Result(String)




	/**
	 * Initializes a new instance of the <b>Result</b> type as specified.
	 *
	 * @param	result
	 * 		The "success value" of this <b>Result</b>.
	 * @param	message
	 * 		A string containing the message to initialize this <b>Result</b> to.
	 * @param	data
	 * 		Any data returned by this <b>Result</b>.
	 */
	public Result( final boolean result, final String message, final Object data ) {

		this.code = 0;
		this.data = data;
		this.message = message;
		this.result = result;

	} // Result(boolean,String,Object)




	public int getCode() {
		return this.code;
	}
	public void setCode( final int code ) {
		this.code = code;
	}



	public Object getData() {
		return this.data;
	}
	public void setData( final Object theObject ) {
		this.data = theObject;
	}




	public String getMessage() {
		return this.message;
	}
	public void setMessage( final String message ) {
		this.message = message;
	}




	public boolean getResult() {
		return this.result;
	}
	public void setResult( final boolean result ) {
		this.result = result;
	}




	/**
	 * Helper method for when you expect the data to be a List.
	 *
	 * @return
	 * 		A generic {@link List} with the expected signature.<br/>
	 * 		If the data was null, this will return an empty List of type {@link ArrayList}.
	 */
	@SuppressWarnings("unchecked")
	public final < T > List< T > getDataList() {

		List< T > retVal = null;

		// null data is null data; don't want:
		if ( this.data != null ) {

			retVal = (List< T >)this.data;

		}

		return ( retVal == null ? new ArrayList<>() : retVal );

	} // getDataList()




	/**
	 * Helper method for when you expect the data to be a Map.
	 *
	 * @return
	 * 		A generic {@link Map} with the expected signature.<br/>
	 * 		If the data was null, this will return an empty Map of type {@link HashMap}.
	 */
	@SuppressWarnings("unchecked")
	public final < K, V > Map< K, V > getDataMap() {

		Map< K, V > retVal = null;

		// null data is null data; don't want:
		if ( this.data != null ) {

			retVal = (Map< K, V >)this.data;
		}

		return ( retVal == null ? new HashMap<>() : retVal );

	} // getDataMap()




	/*public String getJson( final boolean... skipFlags_notUsed ) throws JsonParseException {

		String retVal = EMPTY_STRING;
		final JsonFactory f = new JsonFactory();

		// An IOException is possible on close() as per the Closeable interface:
		try (
			StringWriter sw = new StringWriter();
			JsonGenerator jg = f.createGenerator( sw )
		) {

			//writeObjectAsJson( jg );

			jg.close();	// Important! Will force flushing of output, close underlying output stream.

			// The above is needed to ensure the output is flushed prior to getting the value:
			retVal = sw.toString();

		} catch ( final IOException ex ) {
			throw new JsonParseException( null, "Unable to generate JSON.", ex );
		}

		return retVal;

	} // getJson(boolean...)
*/



	public void setJson( final String json ) throws JsonParseException {		

	} // setJson(String)

	// ^- Implementation: IHasJson --------------------------------------------





} // class Result
