/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.jaxbindex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.JptResourceModelListener;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.resource.jaxbindex.JaxbIndexResource;


public class JaxbIndexResourceImpl
		implements JaxbIndexResource {
	
	protected final ListenerList<JptResourceModelListener> resourceModelListenerList =
			new ListenerList<JptResourceModelListener>(JptResourceModelListener.class);
	
	
	protected final List<String> classNames = new Vector<String>();
	
	protected IFile file;
	
	
	public JaxbIndexResourceImpl(IFile file) {
		super();
		if (file == null) {
			throw new IllegalArgumentException("file cannot be null");
		}
		this.file = file;
		buildClassNames();
	}
	
	
	private void buildClassNames() {
		InputStream stream = null;
		
		try {
			stream = file.getContents();
		}
		catch (CoreException ce) {
			JptJaxbCorePlugin.log(ce);
			return;
		}
		
		if (stream != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			try {
				String line = reader.readLine();
				while (line != null) {
					String className = line.trim();
					this.classNames.add(className);
					line = reader.readLine();
				}
			}
			catch (Exception ex) {
				JptJaxbCorePlugin.log(ex);
			}
			finally {
				try {
					reader.close();
				}
				catch (IOException ioe) {
					JptJaxbCorePlugin.log(ioe);
				}
			}
		}
	}
	
	public Iterable<String> getFullyQualifiedClassNames() {
		String packageName = getPackageName();
		final String packagePrefix = StringTools.stringIsEmpty(packageName) ? "" : packageName + ".";
		return new TransformationIterable<String, String>(
				new SnapshotCloneIterable<String>(this.classNames)) {
			@Override
			protected String transform(String shortClassName) {
				return packagePrefix + shortClassName;
			}
		};
	}
	
	protected String getPackageName() {
		IJavaElement javaElement = JavaCore.create(this.file.getParent());
		if (javaElement != null && javaElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
			return ((IPackageFragment) javaElement).getElementName();
		}
		return null;
	}
	
	void update() {
		this.classNames.clear();
		buildClassNames();
		resourceModelChanged();
	}
	
	
	// ********** JptResourceModel implementation **********
	
	public JptResourceType getResourceType() {
		return JptJaxbCorePlugin.JAXB_INDEX_RESOURCE_TYPE;
	}
	
	public void addResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}
	
	public void removeResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}
	
	protected void resourceModelChanged() {
		for (JptResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged(this);
		}
	}
}
