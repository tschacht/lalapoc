package lalapoc.entity.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lalapoc.entity.Name;
import org.springframework.data.geo.Point;

import java.io.IOException;
import java.io.StringWriter;
import java.time.ZonedDateTime;

public abstract class NameFactory {

	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.registerModule( new JSR310Module() );
	}

	public static Name newName( String name, int people, double lat, double lon, ZonedDateTime time ) {
		Name result = new Name();

		result.setName( name );
		result.setPeople( people );
		result.setPosition( new Point( lon, lat ) ); // latitude -> y-axis (move vertically), longitude -> x-axis (move horizontally)
		result.setTime( time );

		return result;
	}

	public static String newNameJson( String name, int people, double lat, double lon, ZonedDateTime time ) throws IOException {
		Name result = newName( name, people, lat, lon, time );

		StringWriter jsonWriter = new StringWriter();
		mapper.writeValue( jsonWriter, result );

		return jsonWriter.toString();
	}

	public static String newNameJson( String name, int people, ZonedDateTime time ) throws IOException {
		Name result = newName( name, people, 0, 0, time );
		result.setPosition( null );

		StringWriter jsonWriter = new StringWriter();
		mapper.writeValue( jsonWriter, result );

		return jsonWriter.toString();
	}

}
