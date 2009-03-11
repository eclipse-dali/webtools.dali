package org.eclipse.jpt.gen.internal2;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

public class JptGenPlugin {
	public static final String PLUGIN_ID = "org.eclipse.jpt.gen";
	public static void logException ( String msg, Throwable e ) {
		Bundle bundle = Platform.getBundle(PLUGIN_ID);
		ILog log = Platform.getLog(bundle);
		log.log(new Status(IStatus.ERROR, PLUGIN_ID, msg, e ));		
	}
	
	public static void logException( CoreException ce ) {
		IStatus status = ce.getStatus();
		Bundle bundle = Platform.getBundle(PLUGIN_ID);
		ILog log = Platform.getLog(bundle);
		log.log(new Status(IStatus.ERROR, PLUGIN_ID, status.getMessage(), ce));		
	}
}
