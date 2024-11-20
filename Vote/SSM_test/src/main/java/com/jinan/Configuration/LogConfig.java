package com.jinan.Configuration;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
/**
 * 自定义日志保存类
 */
@Configuration
@Slf4j
public class LogConfig extends DBAppenderBase<ILoggingEvent> {
    private static final int CREATE_TIME_INDEX = 1;
    private static final int UPDATE_TIME_INDEX = 2;
    private static final int IP_ADDR=3;
    private static final int MESSAGE_INDEX = 4;
    private static final int LEVEL_STRING_INDEX = 5;
    private static final int LOGGER_NAME_INDEX = 6;
    private static final int CALLER_FILENAME_INDEX = 7;
    private static final int CALLER_CLASS_INDEX = 8;
    private static final int CALLER_METHOD_INDEX = 9;
    private static final int CALLER_LINE_INDEX = 10;

    protected String insertSQL;
    protected static final Method GET_GENERATED_KEYS_METHOD;
    protected static final StackTraceElement EMPTY_CALLER_DATA = CallerData.naInstance();

    private static String buildInsertSQL() {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO system_log ");
        sqlBuilder.append("(create_time, update_time,ip_addr, message, level_string, logger_name, caller_filename, caller_class, caller_method, caller_line) ");
        sqlBuilder.append("VALUES (?, ?,?, ? ,?, ?, ?, ?, ?, ?)");
        return sqlBuilder.toString();
    }

    @Override
    public void start() {
        this.insertSQL = buildInsertSQL();
        super.start();
    }

    @Override
    protected Method getGeneratedKeysMethod() {
        return GET_GENERATED_KEYS_METHOD;
    }

    @Override
    protected String getInsertSQL() {
        return this.insertSQL;
    }

    @Override
    protected void subAppend(ILoggingEvent iLoggingEvent, Connection connection, PreparedStatement preparedStatement) throws Throwable {
        this.bindLoggingEventWithInsertStatement(preparedStatement, iLoggingEvent);
        this.bindCallerDataWithPreparedStatement(preparedStatement, iLoggingEvent.getCallerData());
        int updateCount = preparedStatement.executeUpdate();
        if (updateCount != 1) {
            this.addWarn("Failed to insert loggingEvent");
        }
    }

    @Override
    protected void secondarySubAppend(ILoggingEvent iLoggingEvent, Connection connection, long l) throws Throwable {

    }

    private void bindCallerDataWithPreparedStatement(PreparedStatement preparedStatement, StackTraceElement[] callerDataArray) throws SQLException, SQLException {
        StackTraceElement caller = this.extractFirstCaller(callerDataArray);
        preparedStatement.setString(CALLER_FILENAME_INDEX, caller.getFileName());
        preparedStatement.setString(CALLER_CLASS_INDEX, caller.getClassName());
        preparedStatement.setString(CALLER_METHOD_INDEX, caller.getMethodName());
        preparedStatement.setString(CALLER_LINE_INDEX, Integer.toString(caller.getLineNumber()));
    }


    private StackTraceElement extractFirstCaller(StackTraceElement[] callerDataArray) {
        StackTraceElement caller = EMPTY_CALLER_DATA;
        if (this.hasAtLeastOneNonNullElement(callerDataArray)) {
            caller = callerDataArray[0];
        }
        return caller;
    }

    private boolean hasAtLeastOneNonNullElement(StackTraceElement[] callerDataArray) {
        return callerDataArray != null && callerDataArray.length > 0 && callerDataArray[0] != null;
    }

    private void bindLoggingEventWithInsertStatement(PreparedStatement preparedStatement, ILoggingEvent iLoggingEvent) throws SQLException {
        Timestamp  date = new Timestamp (iLoggingEvent.getTimeStamp());
        preparedStatement.setTimestamp(CREATE_TIME_INDEX, date);
        preparedStatement.setTimestamp(UPDATE_TIME_INDEX, date);
        preparedStatement.setString(IP_ADDR,getUserIP());
        preparedStatement.setString(MESSAGE_INDEX, iLoggingEvent.getFormattedMessage());
        preparedStatement.setString(LEVEL_STRING_INDEX, iLoggingEvent.getLevel().toString());
        preparedStatement.setString(LOGGER_NAME_INDEX, iLoggingEvent.getLoggerName());
    }

    public String getUserIP() {
        ServletRequestAttributes requestAttributes = ServletRequestAttributes.class.
                cast(RequestContextHolder.getRequestAttributes());
        HttpServletRequest contextRequest = requestAttributes.getRequest();

        String remoteAddr = "";
        if (contextRequest != null) {
            remoteAddr = contextRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = contextRequest.getRemoteAddr();
            }
        }
        return remoteAddr;
    }


    static {
        Method getGeneratedKeysMethod;
        try {
            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[])null);
        } catch (Exception var2) {
            getGeneratedKeysMethod = null;
        }

        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
    }
}

