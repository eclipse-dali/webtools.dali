/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Iterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.mappings.IMappedSuperclass;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xml Mapped Superclass</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMappedSuperclass()
 * @model kind="class"
 * @generated
 */
public class XmlMappedSuperclass extends XmlTypeMapping
	implements IMappedSuperclass
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlMappedSuperclass() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_MAPPED_SUPERCLASS;
	}

	public String getKey() {
		return IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<ITable> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<ITable> associatedTablesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAttributeNames() {
		return new TransformationIterator<IPersistentAttribute, String>(new FilteringIterator<IPersistentAttribute>(getPersistentType().attributes()) {
			protected boolean accept(Object o) {
				String key = ((IPersistentAttribute) o).getMappingKey();
				return key == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY || key == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
			}
		}) {
			protected String transform(IPersistentAttribute next) {
				return next.getName();
			}
		};
	}

	public Iterator<String> overridableAssociationNames() {
		return new TransformationIterator<IPersistentAttribute, String>(new FilteringIterator<IPersistentAttribute>(getPersistentType().attributes()) {
			protected boolean accept(Object o) {
				String key = ((IPersistentAttribute) o).getMappingKey();
				return key == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY || key == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
			}
		}) {
			protected String transform(IPersistentAttribute next) {
				return next.getName();
			}
		};
	}

	@Override
	public int xmlSequence() {
		return 0;
	}
}