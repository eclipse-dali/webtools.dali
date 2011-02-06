/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.gen.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

public class JptJpaGenPlugin {
	public static final String PLUGIN_ID = "org.eclipse.jpt.jpa.gen";
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
