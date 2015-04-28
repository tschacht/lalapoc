package lalapoc.entity.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import lalapoc.entity.Name;
import org.springframework.data.geo.Point;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalTime;

public abstract class NameFactory {

	public static Name newName( String name, int people, double lat, double lon, LocalTime time ) {
		Name result = new Name();

		result.setName( name );
		result.setPeople( people );
		result.setPosition( new Point( lon, lat ) );
		result.setTime( time );

		return result;
	}

	public static String newNameJson( String name, int people, double lat, double lon, LocalTime time ) throws IOException {
		Name result = newName( name, people, lat, lon, time );

		StringWriter jsonWriter = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue( jsonWriter, result );

		return jsonWriter.toString();
	}

	public static String newNameJson( String name, int people, LocalTime time ) throws IOException {
		Name result = newName( name, people, 0, 0, time );
		result.setPosition( null );

		StringWriter jsonWriter = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue( jsonWriter, result );

		return jsonWriter.toString();
	}

}
