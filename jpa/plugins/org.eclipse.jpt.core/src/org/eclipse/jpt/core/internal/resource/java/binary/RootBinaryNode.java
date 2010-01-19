/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaResourceModelListener;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.utility.internal.ListenerList;

/**
 * JAR and external types
 */
abstract class RootBinaryNode
	extends BinaryNode
	implements JavaResourceNode.Root
{
	/** pluggable annotation provider */
	private final JpaAnnotationProvider annotationProvider;

	/** listeners notified whenever the resource model changes */
	private final ListenerList<JpaResourceModelListener> resourceModelListenerList = new ListenerList<JpaResourceModelListener>(JpaResourceModelListener.class);


	// ********** construction **********
	
	RootBinaryNode(JavaResourceNode parent, JpaAnnotationProvider annotationProvider) {
		super(parent);
		this.annotationProvider = annotationProvider;
	}


	// ********** overrides **********

	@Override
	protected boolean requiresParent() {
		return false;
	}

	@Override
	public Root getRoot() {
		return this;
	}

	@Override
	public JpaAnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}


	// ********** JavaResourceNode.Root implementation **********

	public void resourceModelChanged() {
		for (JpaResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged(this);
		}
	}


	// ********** JpaResourceModel implementation **********
	
	public JpaResourceType getResourceType() {
		return JptCorePlugin.JAR_RESOURCE_TYPE;
	}

	public void addResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}

	public void removeResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}

}
