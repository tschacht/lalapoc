package lalapoc.entity.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class LalapocJsr310Converters {

	//private static final Logger LOG = LoggerFactory.getLogger( LalapocJsr310Converters.class );
	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.registerModule( new JSR310Module() );
	}

	public static Collection<Converter<?, ?>> getConvertersToRegister() {

		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add( ZonedDateTimeToStringConverter.INSTANCE );
		converters.add( StringToZonedDateTimeConverter.INSTANCE );

		return converters;
	}

	/*
	public static boolean supports( Class<?> type ) {
		return Arrays.<Class<?>>asList( ZonedDateTime.class, String.class ).contains( type );
	}
	*/

	public enum ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {
		INSTANCE;

		@Override
		public String convert( ZonedDateTime source ) {
			if( source == null ) {
				return null;
			} else {
				StringWriter jsonWriter = new StringWriter();
				try {
					mapper.writeValue( jsonWriter, source );
					return jsonWriter.toString();
				} catch( IOException e ) {
					LoggerFactory.getLogger( getClass() ).error( "conversion failed.", e );
					return null;
				}
			}
		}
	}

	public enum StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
		INSTANCE;

		@Override
		public ZonedDateTime convert( String source ) {
			if( source == null ) {
				return null;
			} else {
				try {
					return mapper.readValue( source, ZonedDateTime.class );
				} catch( IOException e ) {
					LoggerFactory.getLogger( getClass() ).error( "conversion failed.", e );
					return null;
				}
			}
		}
	}

}
