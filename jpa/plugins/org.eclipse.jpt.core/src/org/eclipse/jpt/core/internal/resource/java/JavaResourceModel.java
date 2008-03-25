/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.ResourceModelListener;
import org.eclipse.jpt.core.internal.AbstractResourceModel;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.BitTools;

public class JavaResourceModel
	extends AbstractResourceModel
{
	private final Collection<ResourceModelListener> resourceModelListeners;
	
	private final JpaCompilationUnit compilationUnitResource;
	
	
	public JavaResourceModel(
			IFile file, JpaAnnotationProvider annotationProvider, 
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider,
			AnnotationEditFormatter annotationEditFormatter) {
		super();
		this.resourceModelListeners = new ArrayList<ResourceModelListener>();
		this.compilationUnitResource = 
			new JpaCompilationUnit(file, annotationProvider, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter, this);
	}
	
	public String resourceType() {
		return JAVA_RESOURCE_TYPE;
	}
	
	@Override
	public JpaCompilationUnit resource() {
		return this.compilationUnitResource;
	}
	
	public void addResourceModelChangeListener(ResourceModelListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		if (this.resourceModelListeners.contains(listener)) {
			throw new IllegalArgumentException("Listener " + listener + " already added");		
		}
		this.resourceModelListeners.add(listener);
	}
	
	public void removeResourceModelChangeListener(ResourceModelListener listener) {
		if (!this.resourceModelListeners.contains(listener)) {
			throw new IllegalArgumentException("Listener " + listener + " was never added");		
		}
		this.resourceModelListeners.add(listener);
	}

	public void resourceChanged() {
		if (resource() == null) {
			throw new IllegalStateException("Change events should not be fired during construction");
		}
		for (ResourceModelListener listener : this.resourceModelListeners) {
			listener.resourceModelChanged();
		}
	}
	
	public void javaElementChanged(ElementChangedEvent event) {
		synchWithJavaDelta(event.getDelta());
	}
	
	private void synchWithJavaDelta(IJavaElementDelta delta) {
		switch (delta.getElement().getElementType()) {
			case IJavaElement.JAVA_MODEL :
			case IJavaElement.JAVA_PROJECT :
			case IJavaElement.PACKAGE_FRAGMENT_ROOT :
			case IJavaElement.PACKAGE_FRAGMENT :
				this.synchChildrenWithJavaDelta(delta);
				break;
			case IJavaElement.COMPILATION_UNIT :
				this.synchCompilationUnitWithJavaDelta(delta);
				break;
			default :
				break; // the event is somehow lower than a compilation unit
		}
	}

	private void synchChildrenWithJavaDelta(IJavaElementDelta delta) {
		for (IJavaElementDelta child : delta.getAffectedChildren()) {
			this.synchWithJavaDelta(child); // recurse
		}
	}

	private void synchCompilationUnitWithJavaDelta(IJavaElementDelta delta) {
		// ignore changes to/from primary working copy - no content has changed;
		// and make sure there are no other flags set that indicate both a change to/from
		// primary working copy AND content has changed
		if (BitTools.onlyFlagIsSet(delta.getFlags(), IJavaElementDelta.F_PRIMARY_WORKING_COPY)) {
			return;
		}
		if (delta.getElement().equals(this.compilationUnitResource.getCompilationUnit())) {
			//TODO possibly hop on the UI thread here so that we know only 1 thread is changing our model
			this.compilationUnitResource.updateFromJava(JDTTools.buildASTRoot(this.compilationUnitResource.getCompilationUnit()));
		}
	}

	public void resolveTypes() {
		this.compilationUnitResource.resolveTypes(JDTTools.buildASTRoot(this.compilationUnitResource.getCompilationUnit()));
	}
}
