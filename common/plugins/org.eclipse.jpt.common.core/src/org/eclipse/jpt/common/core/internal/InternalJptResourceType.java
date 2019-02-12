/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal;

import java.util.HashSet;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.JptResourceTypeManager;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;
import com.ibm.icu.text.Collator;

/**
 * Resource type.
 */
public class InternalJptResourceType
	implements JptResourceType
{
	private final InternalJptResourceTypeManager manager;
	private final String id;
	private final IContentType contentType;
	private final String version;
	private /* final */ String pluginId;

	// ignore duplicates
	private /* final */ HashSet<InternalJptResourceType> baseTypes;


	InternalJptResourceType(InternalJptResourceTypeManager manager, String id, IContentType contentType, String version) {
		super();
		this.manager = manager;
		this.id = id;
		this.contentType = contentType;
		this.version = version;
	}


	public JptResourceTypeManager getManager() {
		return this.manager;
	}

	public String getId() {
		return this.id;
	}

	public IContentType getContentType() {
		return this.contentType;
	}

	public String getVersion() {
		return this.version;
	}

	void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	public String getPluginId() {
		return this.pluginId;
	}

	void setBaseTypes(HashSet<InternalJptResourceType> baseTypes) {
		this.baseTypes = baseTypes;
	}

	public Iterable<JptResourceType> getBaseTypes() {
		return new SuperIterableWrapper<JptResourceType>(this.baseTypes);
	}

	public boolean isKindOf(JptResourceType resourceType) {
		return this.isKindOf_(resourceType) ||
				this.extends_(resourceType);
	}

	private boolean isKindOf_(JptResourceType resourceType) {
		return this.contentType.isKindOf(resourceType.getContentType()) &&
				this.versionIsGTE(resourceType);
	}

	private boolean versionIsGTE(JptResourceType resourceType) {
		return this.compareVersionTo(resourceType.getVersion()) >= 0;
	}

	private boolean extends_(JptResourceType resourceType) {
		for (JptResourceType baseType : this.baseTypes) {
			if (baseType.isKindOf(resourceType)) {
				return true;
			}
		}
		return false;
	}

	public int compareTo(JptResourceType resourceType) {
		int result = Collator.getInstance().compare(this.contentType, resourceType.getContentType());
		return (result != 0) ? result : this.compareVersionTo(resourceType.getVersion());
	}

	/**
	 * {@link #UNDETERMINED_VERSION} is less than any other version.
	 */
	private int compareVersionTo(String v) {
		if (this.version.equals(UNDETERMINED_VERSION)) {
			return v.equals(UNDETERMINED_VERSION) ? 0 : -1;
		}
		if (v.equals(UNDETERMINED_VERSION)) {
			return 1;
		}
		return ComparatorTools.integerVersionComparator().compare(this.version, v);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.contentType.getName());
		if ( ! this.version.equals(UNDETERMINED_VERSION)) {
			sb.append(' ');
			sb.append(this.version);
		}
		return sb.toString();
	}
}
