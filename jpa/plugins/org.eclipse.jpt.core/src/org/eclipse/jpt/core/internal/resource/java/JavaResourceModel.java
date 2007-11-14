/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jpt.core.internal.IJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.IResourceModelListener;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationEditFormatter;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.utility.internal.BitTools;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;

public class JavaResourceModel implements IResourceModel
{
	private final JpaCompilationUnitResource compilationUnitResource;
	
	private final Collection<IResourceModelListener> resourceModelListeners;
	
	
	public JavaResourceModel(
			IFile file, IJpaAnnotationProvider annotationProvider, 
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider,
			AnnotationEditFormatter annotationEditFormatter) {
		super();
		this.resourceModelListeners = new ArrayList<IResourceModelListener>();
		this.compilationUnitResource = 
			new JpaCompilationUnitResource(file, annotationProvider, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter, this);
	}
	
	public JpaCompilationUnitResource getCompilationUnitResource() {
		return this.compilationUnitResource;
	}
	
	public String getResourceType() {
		return JAVA_RESOURCE_TYPE;
	}
	
	public void handleJavaElementChangedEvent(ElementChangedEvent event) {
		synchWithJavaDelta(event.getDelta());
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public IJpaContentNode getContentNode(int offset) {
		// TODO Auto-generated method stub
		return null;
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

	public void addResourceModelChangeListener(IResourceModelListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		if (this.resourceModelListeners.contains(listener)) {
			throw new IllegalArgumentException("Listener " + listener + " already added");		
		}
		this.resourceModelListeners.add(listener);
	}
	
	public void removeResourceModelChangeListener(IResourceModelListener listener) {
		if (!this.resourceModelListeners.contains(listener)) {
			throw new IllegalArgumentException("Listener " + listener + " was never added");		
		}
		this.resourceModelListeners.add(listener);
	}

	public void resourceChanged() {
		if (this.compilationUnitResource == null) {
			throw new IllegalStateException("Change events should not be fired during construction");
		}
		for (IResourceModelListener listener : this.resourceModelListeners) {
			listener.resourceModelChanged();
		}
	}
}
