package TheApp275Final.term.utility;

import java.time.LocalDateTime;
import java.sql.Timestamp;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;



@Converter(autoApply=true)
public class LocalDateTimeJPAConverter implements AttributeConverter<LocalDateTime,Timestamp> {

	
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		return (localDateTime == null ? null : Timestamp.valueOf(localDateTime));
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timeStamp) {
		return (timeStamp == null ? null : timeStamp.toLocalDateTime());
	}

}
