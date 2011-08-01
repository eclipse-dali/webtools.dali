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

import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceTypeCache;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;

/**
 * cache used to hold binary "external" Java resource types
 * (typically derived from JARs on the project's build path)
 */
public final class BinaryTypeCache
	extends RootBinaryNode
	implements JavaResourceTypeCache
{
	/** populated on-demand */
	private final Vector<Entry> entries = new Vector<Entry>();


	// ********** construction **********

	public BinaryTypeCache(AnnotationProvider annotationProvider) {
		super(null, annotationProvider);
	}


	// ********** JavaResourceNode.Root implementation **********

	public Iterable<JavaResourceAbstractType> getTypes() {
		return new TransformationIterable<Entry, JavaResourceAbstractType>(this.getEntries()) {
			@Override
			protected JavaResourceAbstractType transform(Entry entry) {
				return entry.type;
			}
		};
	}

	private Iterable<Entry> getEntries() {
		return new LiveCloneIterable<Entry>(this.entries);
	}


	// ********** JavaResourcePersistentTypeCache implementation **********

	public int getTypesSize() {
		return this.entries.size();
	}

	public JavaResourceAbstractType addType(IType jdtType) {
		Entry entry = this.buildEntry(jdtType);
		this.entries.add(entry);
		this.fireItemAdded(TYPES_COLLECTION, entry.type);
		return entry.type;
	}

	private Entry buildEntry(IType jdtType) {
		return new Entry(this.buildType(jdtType), jdtType.getResource());
	}

	//ignore annotations
	private JavaResourceAbstractType buildType(IType jdtType) {
		try {
			if (jdtType.isClass() || jdtType.isInterface()) {
				return new BinaryType(this, jdtType);
			}
			if (jdtType.isEnum()) {
				return new BinaryEnum(this, jdtType);
			}
		}
		catch(JavaModelException e) {
			JptCommonCorePlugin.log(e);
		}
		return null;
	}

	public boolean removeTypes(IFile jarFile) {
		boolean modified = false;
		for (Entry entry : this.getEntries()) {
			IResource resource = entry.resource;
			if ((resource != null) && resource.equals(jarFile)) {
				this.removeEntry(entry);
				modified = true;
			}
		}
		return modified;
	}

	private void removeEntry(Entry entry) {
		this.entries.remove(entry);
		this.fireItemRemoved(TYPES_COLLECTION, entry.type);
	}


	// ********** overrides **********

	/**
	 * Ignore changes to this collection. Adds can be ignored since they are triggered
	 * by requests that will, themselves, trigger updates (typically during the
	 * update of an object that calls a setter with the newly-created resource
	 * type). Deletes will be accompanied by manual updates.
	 */
	@Override
	protected void aspectChanged(String aspectName) {
		if ((aspectName != null) && ! aspectName.equals(TYPES_COLLECTION)) {
			super.aspectChanged(aspectName);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.entries);
	}


	// ********** cache entry **********

	/**
	 * Associate a type with its resource.
	 * This will be a JAR in the case of a type loaded from a JAR that is in
	 * the Eclipse workspace. The resource will be null for a type loaded
	 * from a JAR or class directory outside of the workspace.
	 */
	static class Entry {
		final JavaResourceAbstractType type;
		final IResource resource;

		Entry(JavaResourceAbstractType type, IResource resource) {
			super();
			this.type = type;
			this.resource = resource;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.type);
		}

	}

}
