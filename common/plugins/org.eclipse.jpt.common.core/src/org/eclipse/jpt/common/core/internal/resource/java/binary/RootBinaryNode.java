/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceModelListener;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.utility.internal.ListenerList;

/**
 * JAR and external types
 */
abstract class RootBinaryNode
	extends BinaryNode
	implements JavaResourceNode.Root
{
	/** pluggable annotation provider */
	private final AnnotationProvider annotationProvider;

	/** listeners notified whenever the resource model changes */
	private final ListenerList<JptResourceModelListener> resourceModelListenerList = new ListenerList<JptResourceModelListener>(JptResourceModelListener.class);


	// ********** construction **********
	
	RootBinaryNode(JavaResourceNode parent, AnnotationProvider annotationProvider) {
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
	public AnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}


	// ********** JavaResourceNode.Root implementation **********

	public void resourceModelChanged() {
		for (JptResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged(this);
		}
	}


	// ********** JptResourceModel implementation **********
	
	public JptResourceType getResourceType() {
		return JptCommonCorePlugin.JAR_RESOURCE_TYPE;
	}

	public void addResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}

	public void removeResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}

}
