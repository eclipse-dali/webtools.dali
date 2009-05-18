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

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentTypeCache;
import org.eclipse.jpt.utility.internal.iterables.CloneIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * cache used to hold binary "external" Java resource persistent types
 * (typically derived from JARs on the project's build path)
 */
public final class BinaryPersistentTypeCache
	extends RootBinaryNode
	implements JavaResourcePersistentTypeCache
{
	/** populated on-demand */
	private final Vector<Entry> entries = new Vector<Entry>();


	// ********** construction **********

	public BinaryPersistentTypeCache(JpaAnnotationProvider annotationProvider) {
		super(null, annotationProvider);
	}


	// ********** JavaResourceNode.Root implementation **********

	public Iterator<JavaResourcePersistentType> persistentTypes() {
		return this.getPersistentTypes().iterator();
	}

	private Iterable<JavaResourcePersistentType> getPersistentTypes() {
		return new TransformationIterable<Entry, JavaResourcePersistentType>(this.getEntries()) {
			@Override
			protected JavaResourcePersistentType transform(Entry entry) {
				return entry.persistentType;
			}
		};
	}

	private Iterable<Entry> getEntries() {
		return new CloneIterable<Entry>(this.entries);
	}


	// ********** JavaResourcePersistentTypeCache implementation **********

	public int persistentTypesSize() {
		return this.entries.size();
	}

	public JavaResourcePersistentType addPersistentType(IType jdtType) {
		Entry entry = this.buildEntry(jdtType);
		this.entries.add(entry);
		this.fireItemAdded(PERSISTENT_TYPES_COLLECTION, entry.persistentType);
		return entry.persistentType;
	}

	private Entry buildEntry(IType jdtType) {
		return new Entry(this.buildPersistentType(jdtType), jdtType.getResource());
	}

	private JavaResourcePersistentType buildPersistentType(IType jdtType) {
		return new BinaryPersistentType(this, jdtType);
	}

	public boolean removePersistentTypes(IFile jarFile) {
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
		this.fireItemRemoved(PERSISTENT_TYPES_COLLECTION, entry.persistentType);
	}


	// ********** overrides **********

	@Override
	public IFile getFile() {
		return null;  // ???
	}

	/**
	 * Ignore changes to this collection. Adds can be ignored since they are triggered
	 * by requests that will, themselves, trigger updates (typically during the
	 * update of an object that calls a setter with the newly-created resource
	 * type). Deletes will be accompanied by manual updates.
	 */
	@Override
	protected void aspectChanged(String aspectName) {
		if ((aspectName != null) && ! aspectName.equals(PERSISTENT_TYPES_COLLECTION)) {
			super.aspectChanged(aspectName);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.entries);
	}


	// ********** cache entry **********

	/**
	 * Associate a persistent type with its resource.
	 * This will be a JAR in the case of a type loaded from a JAR that is in
	 * the Eclipse workspace. The resource will be null for a type loaded
	 * from a JAR or class directory outside of the workspace.
	 */
	static class Entry {
		final JavaResourcePersistentType persistentType;
		final IResource resource;

		Entry(JavaResourcePersistentType persistentType, IResource resource) {
			super();
			this.persistentType = persistentType;
			this.resource = resource;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.persistentType);
		}

	}

}
