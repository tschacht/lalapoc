package lalapoc.entity.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import lalapoc.entity.Name;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalTime;

public abstract class NameFactory {

	public static Name newName( String name, int people, String position, LocalTime time ) {
		Name result = new Name();

		result.setName( name );
		result.setPeople( people );
		result.setPosition( position );
		result.setTime( time );

		return result;
	}

	public static String newNameJson( String name, int people, String position, LocalTime time ) throws IOException {
		Name result = newName( name, people, position, time );

		StringWriter jsonWriter = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue( jsonWriter, result );

		return jsonWriter.toString();
	}

}
