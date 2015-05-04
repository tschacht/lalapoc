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

	public static String newNameJson( String nameStr, int people, double lat, double lon, ZonedDateTime time ) throws IOException {
		Name name = newName( nameStr, people, lat, lon, time );

		StringWriter jsonWriter = new StringWriter();
		mapper.writeValue( jsonWriter, name );

		return jsonWriter.toString();
	}

	public static String newNameJson( String nameStr, int people, ZonedDateTime time ) throws IOException {
		Name name = newName( nameStr, people, 0, 0, time );
		name.setPosition( null );

		StringWriter jsonWriter = new StringWriter();
		mapper.writeValue( jsonWriter, name );

		return jsonWriter.toString();
	}

}
