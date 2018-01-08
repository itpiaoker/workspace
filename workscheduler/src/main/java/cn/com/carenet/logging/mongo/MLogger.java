package cn.com.carenet.logging.mongo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carenet.logging.LogWritter;

public class MLogger {
	private static Logger LOG;
	private static final char SPACE = ' ';
	private static Class<?> clazz;
	private LogWritter stream = new MongoWritter();
	private String workFlowType;
	private String workFlowID;

	MLogger(Class<?> clazz) {
		LOG = LoggerFactory.getLogger(clazz);
		MLogger.clazz = clazz;
	}

	public void info(String msg) {
		LOG.info(msg);
		writeLog(msg, Level.INFO, false);
	}

	public void info(String format, Object... arguments) {
		LOG.info(format, arguments);
		writeLog(format, Level.INFO, false, arguments);
	}

	public void debug(String msg) {
		LOG.info(msg);
		writeLog(msg, Level.DEBUG, false);
	}

	public void debug(String format, Object... arguments) {
		LOG.debug(format, arguments);
		writeLog(format, Level.DEBUG, false, arguments);
	}

	public void error(String msg) {
		LOG.error(msg);
		writeLog(msg, Level.ERROR, false);
	}

	public void error(String format, Object... arguments) {
		LOG.error(format, arguments);
		writeLog(format, Level.ERROR, false, arguments);
	}

	public void warn(String msg) {
		LOG.warn(msg);
		writeLog(msg, Level.WARN, false);
	}

	public void warn(String format, Object... arguments) {
		LOG.warn(format, arguments);
		writeLog(format, Level.WARN, false, arguments);
	}

	public void trace(String msg) {
		LOG.trace(msg);
		writeLog(msg, Level.TRACE, false);
	}

	public void trace(String format, Object... arguments) {
		LOG.trace(format, arguments);
		writeLog(format, Level.TRACE, false, arguments);
	}

	public void writeLog(String format, Level level, boolean showContextMap, Object... arguments) {
		Object[] argArray = new Object[] { arguments };

		final StringBuilder sb = new StringBuilder();

		String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
		DateFormat dateFormatter = new SimpleDateFormat(dateTimeFormat);
		String dateText = dateFormatter.format(new Date());
		sb.append("[");
		sb.append(dateText);
		sb.append(SPACE);
		sb.append(level.toString()).append("]");
		sb.append(SPACE);
		sb.append(clazz.getSimpleName());
		sb.append("--");
		sb.append(getFormattedMessage(format, argArray, null));
		if (showContextMap) {
			final Map<String, String> mdc = ThreadContext.getContext();
			if (mdc.size() > 0) {
				sb.append(SPACE);
				sb.append(mdc.toString());
				sb.append(SPACE);
			}
		}
		try {
			stream.writeLine(sb.toString(), workFlowID, workFlowType);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	public String getFormattedMessage(String messagePattern, Object[] argArray, Throwable throwable) {
		Message message = getMessage(messagePattern, argArray, throwable);
		String formattedMessage = message.getFormattedMessage();
		return formattedMessage;
	}

	protected Message getMessage(final String msgPattern, final Object[] args, final Throwable aThrowable) {
		return new ParameterizedMessage(msgPattern, args, aThrowable);
	}

	public void setWorkFlowType(String workFlowType) {
		this.workFlowType = workFlowType;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}
}
