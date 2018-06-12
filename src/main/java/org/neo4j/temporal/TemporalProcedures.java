package org.neo4j.temporal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;
import org.neo4j.procedure.UserFunction;
import org.neo4j.values.storable.DurationValue;

public class TemporalProcedures
{


    @UserFunction( "apoc.temporal.format" )
    @Description( "apoc.temporal.format(input, format) | Format a temporal value" )
    public String format(
            @Name( "temporal" ) Object input,
            @Name( value = "format", defaultValue = "yyyy-MM-dd") String format
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        if ( input instanceof LocalDate ) {
            return ((LocalDate) input).format(formatter);
        }
        else if ( input instanceof ZonedDateTime ) {
            return ((ZonedDateTime) input).format(formatter);
        }
        else if ( input instanceof LocalDateTime ) {
            return ((LocalDateTime) input).format(formatter);
        }
        else if ( input instanceof LocalTime ) {
            return ((LocalTime) input).format(formatter);
        }
        else if ( input instanceof OffsetTime ) {
            return ((OffsetTime) input).format(formatter);
        }
        else if ( input instanceof DurationValue ) {
            return formatDuration( (DurationValue) input, format);
        }

        return input.toString();
    }

    public String formatDuration(DurationValue input, String format) {
        format = format.replace("YYYY", input.get("years").prettyPrint());
        format = format.replace("MM", input.get("monthsofyear").prettyPrint());
        format = format.replace("dd", input.get("days").prettyPrint());

        format = format.replace("HH", input.get("hours").prettyPrint());
        format = format.replace("mm", input.get("minutesofhour").prettyPrint());
        format = format.replace("SSSS", input.get("millisecondsofsecond").prettyPrint());
        format = format.replace("ss", input.get("secondsofminute").prettyPrint());

        return format;
    }


}
