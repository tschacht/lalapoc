package lalapoc.entity.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lalapoc.entity.Need;

import java.io.IOException;
import java.io.StringWriter;

public abstract class NeedFactory {

	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.registerModule( new JSR310Module() );
	}

	public static Need newNeed( String description ) {
		Need result = new Need();
		result.setDescription( description );
		return result;
	}

	public static String newNeedJSON( String description ) throws IOException {
		Need need = newNeed( description );

		StringWriter jsonWriter = new StringWriter();
		mapper.writeValue( jsonWriter, need );

		return jsonWriter.toString();
	}

}
