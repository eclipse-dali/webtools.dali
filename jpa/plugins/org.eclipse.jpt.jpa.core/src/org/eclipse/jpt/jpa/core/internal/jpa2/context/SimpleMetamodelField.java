/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context;

import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;

/**
 * Straightforward implementation.
 */
public class SimpleMetamodelField
	implements MetamodelField
{
	protected final Iterable<String> modifiers;
	protected final String typeName;
	protected final Iterable<String> typeArgumentNames;
	protected final String name;

	public SimpleMetamodelField(
			Iterable<String> modifiers,
			String typeName,
			Iterable<String> typeArgumentNames,
			String name
	) {
		super();
		if (modifiers == null) {
			throw new NullPointerException();
		}
		if (typeName == null) {
			throw new NullPointerException();
		}
		if (typeArgumentNames == null) {
			throw new NullPointerException();
		}
		if (name == null) {
			throw new NullPointerException();
		}
		this.modifiers = modifiers;
		this.typeName = typeName;
		this.typeArgumentNames = typeArgumentNames;
		this.name = name;
	}

	public Iterable<String> getModifiers() {
		return this.modifiers;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public Iterable<String> getTypeArgumentNames() {
		return this.typeArgumentNames;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return CollectionTools.hashCode(this.modifiers) ^
					this.typeName.hashCode() ^
					CollectionTools.hashCode(this.typeArgumentNames) ^
					this.name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof MetamodelField)) {
			return false;
		}
		MetamodelField other = (MetamodelField) o;
		return CollectionTools.elementsAreEqual(this.getModifiers(), other.getModifiers()) &&
					this.getTypeName().equals(other.getTypeName()) &&
					CollectionTools.elementsAreEqual(this.getTypeArgumentNames(), other.getTypeArgumentNames()) &&
					this.getName().equals(other.getName());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String modifier : this.modifiers) {
			sb.append(modifier);
			sb.append(' ');
		}
		sb.append(this.typeName);
		sb.append('<');
		for (String typeArgumentName : this.typeArgumentNames) {
			sb.append(typeArgumentName);
			sb.append(", "); //$NON-NLS-1$
		}
		sb.setLength(sb.length() - 2);
		sb.append('>');
		sb.append(' ');
		sb.append(this.name);
		return sb.toString();
	}

}
