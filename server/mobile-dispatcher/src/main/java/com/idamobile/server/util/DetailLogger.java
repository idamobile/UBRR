package com.idamobile.server.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * 
 * Detail logging around advice - logs detail information about method calls and exceptions.
 * To be used upon certain class or method it shoud be marked with a @DetailLog annotation
 *
 */
@Component
@Aspect
public class DetailLogger {
    @Pointcut("execution(* *(..)) && (@within(DetailLog) || @annotation(DetailLog))")
    public void callMethod() {
    }

    private Logger logger = Logger.getLogger(this.getClass());
    
    @Around("callMethod()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = "";
        if (joinPoint.getTarget() != null && joinPoint.getTarget().getClass() != null) {
            className = joinPoint.getTarget().getClass().getName();
        }
        
        if (logger.isDebugEnabled()) {
            StringBuffer logMessageStringBuffer = new StringBuffer();
            logMessageStringBuffer.append("\nCALL " + className);
            logMessageStringBuffer.append(".");
            logMessageStringBuffer.append(joinPoint.getSignature().getName());
            
            int i = 0;
            for (Object arg: joinPoint.getArgs()) {
                i++;
                if (arg==null) {
                    logMessageStringBuffer.append("\nPARAM #" + i + ":\n");
                    logMessageStringBuffer.append("NULL");
                }
                else {
                    logMessageStringBuffer.append("\nPARAM #" + i + " " + arg.getClass().getName() + ":\n");
                    logMessageStringBuffer.append(arg.toString());
                }
            }
            logger.debug(logMessageStringBuffer.toString());
        }

        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            Object retVal = joinPoint.proceed();

            stopWatch.stop();
            
            if (logger.isDebugEnabled()) {
                StringBuffer logMessageStringBuffer = new StringBuffer();
                logMessageStringBuffer.append("\nDONE "  + className);
                logMessageStringBuffer.append(".");
                logMessageStringBuffer.append(joinPoint.getSignature().getName());
                logMessageStringBuffer.append("\nTIME: ");
                logMessageStringBuffer.append(stopWatch.getTotalTimeMillis());
                logMessageStringBuffer.append(" ms");
                logMessageStringBuffer.append("\nRETURN:\n");
                logMessageStringBuffer.append(retVal);
    
                logger.debug(logMessageStringBuffer.toString());
            }
            return retVal;
        }
        catch (Throwable e) {
            StringBuffer logMessageStringBuffer = new StringBuffer();
            logMessageStringBuffer.append("\nERROR " + className);
            logMessageStringBuffer.append(".");
            logMessageStringBuffer.append(joinPoint.getSignature().getName());
            
            int i = 0;
            for (Object arg: joinPoint.getArgs()) {
                i++;
                if (arg==null) {
                    logMessageStringBuffer.append("\nPARAM #" + i + ":\n");
                    logMessageStringBuffer.append("NULL");
                }
                else {
                    logMessageStringBuffer.append("\nPARAM #" + i + " " + arg.getClass().getName() + ":\n");
                    logMessageStringBuffer.append(arg.toString());
                }
            }
            logMessageStringBuffer.append("\nTHROW:");
            logMessageStringBuffer.append(e.getMessage());
            logger.error(logMessageStringBuffer.toString());
            
            throw e;
        }
    }

}
