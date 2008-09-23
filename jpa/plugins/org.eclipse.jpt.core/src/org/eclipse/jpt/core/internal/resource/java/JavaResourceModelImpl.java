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
import org.eclipse.jpt.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.resource.java.JpaCompilationUnit;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.BitTools;

// TODO we need access to the JPA platform
public class JavaResourceModelImpl
	extends AbstractResourceModel
	implements JavaResourceModel
{
	private final Collection<ResourceModelListener> resourceModelListeners;
	
	private final JpaCompilationUnit jpaCompilationUnit;
	
	
	public JavaResourceModelImpl(
			IFile file, JpaAnnotationProvider annotationProvider, 
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider,
			AnnotationEditFormatter annotationEditFormatter) {
		super(file);
		this.resourceModelListeners = new ArrayList<ResourceModelListener>();
		// TODO use JPA factory, via the platform
		this.jpaCompilationUnit = 
			new JpaCompilationUnitImpl(file, annotationProvider, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter, this);
	}
	
	public String getResourceType() {
		return JAVA_RESOURCE_TYPE;
	}
	
	public JpaCompilationUnit getJpaCompilationUnit() {
		return this.jpaCompilationUnit;
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
		if (this.jpaCompilationUnit == null) {
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
			case IJavaElement.JAVA_PROJECT :
				if (this.updateOnClasspathChanges(delta)) {
					break;
				}
			case IJavaElement.JAVA_MODEL :
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
		if (delta.getKind() == IJavaElementDelta.REMOVED) {
			//we get the java notification for removal before we get the resource notification.
			//we do not need to handle this event and will get exceptions building an astRoot if we try.
			return;
		}
		if (delta.getElement().equals(this.jpaCompilationUnit.getCompilationUnit())) {
			//TODO possibly hop on the UI thread here so that we know only 1 thread is changing our model
			this.jpaCompilationUnit.updateFromJava();
		}
	}

	//bug 235384 - we need to update all compilation units when a classpath change occurs.
	//The persistence.jar could have been added/removed from the classpath which affects
	//whether we know about the jpa annotations or not.
	private boolean updateOnClasspathChanges(IJavaElementDelta delta) {
		if (BitTools.flagIsSet(delta.getFlags(), IJavaElementDelta.F_RESOLVED_CLASSPATH_CHANGED) ||
			BitTools.flagIsSet(delta.getFlags(), IJavaElementDelta.F_CLASSPATH_CHANGED)) {
				this.jpaCompilationUnit.updateFromJava();
				return true;
		}
		return false;
	}

	public void resolveTypes() {
		this.jpaCompilationUnit.resolveTypes();
	}

}
