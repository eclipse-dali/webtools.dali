/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaMultiRelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaOrderable;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java multi-relationship (m:m, 1:m) mapping
 */
public abstract class AbstractJavaMultiRelationshipMapping<T extends RelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<T> 
	implements JavaMultiRelationshipMapping
{
	protected final JavaOrderable orderable;

	protected String specifiedMapKey;
	protected boolean noMapKey = false;
	protected boolean pkMapKey = false;
	protected boolean customMapKey = false;

	protected String specifiedMapKeyClass;
	protected String defaultMapKeyClass;

	protected AbstractJavaMultiRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.orderable = getJpaFactory().buildJavaOrderable(this, buildOrderableOwner());
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.orderable.initialize();
		this.initializeMapKey();
		this.specifiedMapKeyClass = this.getResourceMapKeyClass();
		this.defaultMapKeyClass = this.buildDefaultMapKeyClass();
	}

	@Override
	protected void update() {
		super.update();
		this.orderable.update();
		this.updateMapKey();
		this.setSpecifiedMapKeyClass_(this.getResourceMapKeyClass());
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
	}

	// ********** AbstractJavaAttributeMapping implementation **********  

	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.JOIN_TABLE);
		names.add(JPA.MAP_KEY);
		names.add(JPA.ORDER_BY);
		if (this.isJpa2_0Compatible()) {
			names.add(JPA2_0.MAP_KEY_CLASS);
			names.add(JPA2_0.MAP_KEY_COLUMN);
			names.add(JPA2_0.MAP_KEY_ENUMERATED);
			names.add(JPA2_0.MAP_KEY_JOIN_COLUMN);
			names.add(JPA2_0.MAP_KEY_JOIN_COLUMNS);
			names.add(JPA2_0.MAP_KEY_TEMPORAL);
			names.add(JPA2_0.ORDER_COLUMN);
		}
	}

	// ********** AbstractJavaRelationshipMapping implementation **********  

	@Override
	protected String buildDefaultTargetEntity() {
		return this.getPersistentAttribute().getMultiReferenceTargetTypeName();
	}


	// ********** ordering **********  

	public JavaOrderable getOrderable() {
		return this.orderable;
	}

	protected Orderable2_0.Owner buildOrderableOwner() {
		return new Orderable2_0.Owner() {
			public String getTableName() {
				return getRelationshipReference().getPredominantJoiningStrategy().getTableName();
			}
			public Table getDbTable(String tableName) {
				return getRelationshipReference().getPredominantJoiningStrategy().getDbTable(tableName);
			}
		};
	}
	
	// ********** Fetchable implementation **********  

	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}


	// ********** CollectionMapping implementation **********  

	public PersistentType getResolvedTargetType() {
		return getResolvedTargetEntity() == null ? null : getResolvedTargetEntity().getPersistentType();
	}

	// ********** map key **********  

	public String getMapKey() {
		if (this.noMapKey) {
			return null;
		}
		if (this.pkMapKey) {
			return this.getTargetEntityPrimaryKeyAttributeName();
		}
		if (this.customMapKey) {
			return this.specifiedMapKey;
		}
		throw new IllegalStateException("unknown map key"); //$NON-NLS-1$
	}

	public String getSpecifiedMapKey() {
		return this.specifiedMapKey;
	}

	public void setSpecifiedMapKey(String mapKey) {
		String old = this.specifiedMapKey;
		this.specifiedMapKey = mapKey;
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (mapKey == null) {
			if (mapKeyAnnotation != null) {
				this.removeMapKeyAnnotation();
			}
		} else {
			if (mapKeyAnnotation == null) {
				mapKeyAnnotation = this.addMapKeyAnnotation();
			}
			mapKeyAnnotation.setName(mapKey);
		}
		this.firePropertyChanged(SPECIFIED_MAP_KEY_PROPERTY, old, mapKey);
	}

	protected void setSpecifiedMapKey_(String mapKey) {
		String old = this.specifiedMapKey;
		this.specifiedMapKey = mapKey;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_PROPERTY, old, mapKey);
	}

	protected void initializeMapKey() {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (mapKeyAnnotation == null) {
			this.noMapKey = true;
		} else {
			this.specifiedMapKey = mapKeyAnnotation.getName();
			if (this.specifiedMapKey == null) {
				this.pkMapKey = true;
			} else {
				this.customMapKey = true;
			}
		}
	}

	protected void updateMapKey() {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (mapKeyAnnotation == null) {
			this.setSpecifiedMapKey_(null);
			this.setNoMapKey_(true);
			this.setPkMapKey_(false);
			this.setCustomMapKey_(false);
		} else {
			String mk = mapKeyAnnotation.getName();
			this.setSpecifiedMapKey_(mk);
			this.setNoMapKey_(false);
			this.setPkMapKey_(mk == null);
			this.setCustomMapKey_(mk != null);
		}
	}

	protected MapKeyAnnotation getMapKeyAnnotation() {
		return (MapKeyAnnotation) this.getResourcePersistentAttribute().getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected MapKeyAnnotation addMapKeyAnnotation() {
		return (MapKeyAnnotation) this.getResourcePersistentAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected boolean mapKeyNameTouches(int pos, CompilationUnit astRoot) {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		return (mapKeyAnnotation != null) && mapKeyAnnotation.nameTouches(pos, astRoot);
	}


	// ********** no map key **********  

	public boolean isNoMapKey() {
		return this.noMapKey;
	}

	public void setNoMapKey(boolean noMapKey) {
		boolean old = this.noMapKey;
		this.noMapKey = noMapKey;
		if (noMapKey) {
			if (this.getMapKeyAnnotation() != null) {
				this.removeMapKeyAnnotation();
			}
		} else {
			// the 'noMapKey' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setNoMapKey_(boolean)
		}
		this.firePropertyChanged(NO_MAP_KEY_PROPERTY, old, noMapKey);
	}

	protected void setNoMapKey_(boolean noMapKey) {
		boolean old = this.noMapKey;
		this.noMapKey = noMapKey;
		this.firePropertyChanged(NO_MAP_KEY_PROPERTY, old, noMapKey);	
	}


	// ********** pk map key **********  

	public boolean isPkMapKey() {
		return this.pkMapKey;
	}

	public void setPkMapKey(boolean pkMapKey) {
		boolean old = this.pkMapKey;
		this.pkMapKey = pkMapKey;
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (pkMapKey) {
			if (mapKeyAnnotation == null) {
				this.addMapKeyAnnotation();
			} else {
				mapKeyAnnotation.setName(null);
			}
		} else {
			// the 'pkMapKey' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setPkMapKey_(boolean)
		}
		this.firePropertyChanged(PK_MAP_KEY_PROPERTY, old, pkMapKey);
	}

	protected void setPkMapKey_(boolean pkMapKey) {
		boolean old = this.pkMapKey;
		this.pkMapKey = pkMapKey;
		this.firePropertyChanged(PK_MAP_KEY_PROPERTY, old, pkMapKey);
	}


	// ********** custom map key **********  

	public boolean isCustomMapKey() {
		return this.customMapKey;
	}

	public void setCustomMapKey(boolean customMapKey) {
		boolean old = this.customMapKey;
		this.customMapKey = customMapKey;
		if (customMapKey) {
			this.setSpecifiedMapKey(""); //$NON-NLS-1$
		} else {
			// the 'customMapKey' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setCustomMapKey_(boolean)
		}
		this.firePropertyChanged(CUSTOM_MAP_KEY_PROPERTY, old, customMapKey);
	}

	protected void setCustomMapKey_(boolean customMapKey) {
		boolean old = this.customMapKey;
		this.customMapKey = customMapKey;
		this.firePropertyChanged(CUSTOM_MAP_KEY_PROPERTY, old, customMapKey);
	}


	// *************** map key class *************

	public String getMapKeyClass() {
		return (this.specifiedMapKeyClass != null) ? this.specifiedMapKeyClass : this.defaultMapKeyClass;
	}

	public String getSpecifiedMapKeyClass() {
		return this.specifiedMapKeyClass;
	}

	public void setSpecifiedMapKeyClass(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		MapKeyClass2_0Annotation mapKeyClassAnnotation = this.getMapKeyClassAnnotation();
		if (mapKeyClass == null) {
			if (mapKeyClassAnnotation != null) {
				this.removeMapKeyClassAnnotation();
			}
		} else {
			if (mapKeyClassAnnotation == null) {
				mapKeyClassAnnotation = this.addMapKeyClassAnnotation();
			}
			mapKeyClassAnnotation.setValue(mapKeyClass);
		}

		this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected void setSpecifiedMapKeyClass_(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected String getResourceMapKeyClass() {
		MapKeyClass2_0Annotation annotation = getMapKeyClassAnnotation();
		return annotation == null ? null : annotation.getValue();
	}

	public String getDefaultMapKeyClass() {
		return this.defaultMapKeyClass;
	}

	protected void setDefaultMapKeyClass(String targetClass) {
		String old = this.defaultMapKeyClass;
		this.defaultMapKeyClass = targetClass;
		this.firePropertyChanged(DEFAULT_MAP_KEY_CLASS_PROPERTY, old, targetClass);
	}

	protected String buildDefaultMapKeyClass() {
		return this.getPersistentAttribute().getMultiReferenceMapKeyTypeName();
	}

	public char getMapKeyClassEnclosingTypeSeparator() {
		return '.';
	}

	protected MapKeyClass2_0Annotation getMapKeyClassAnnotation() {
		return (MapKeyClass2_0Annotation) this.getResourcePersistentAttribute().getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected MapKeyClass2_0Annotation addMapKeyClassAnnotation() {
		return (MapKeyClass2_0Annotation) this.getResourcePersistentAttribute().addAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyClassAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	// ********** Java completion proposals **********  

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getOrderable().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mapKeyNameTouches(pos, astRoot)) {
			return this.javaCandidateMapKeyNames(filter);
		}
		return null;
	}

	protected Iterator<String> javaCandidateMapKeyNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateMapKeyNames(filter));
	}

	protected Iterator<String> candidateMapKeyNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateMapKeyNames(), filter);
	}

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}


	// ********** metamodel **********  

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((JavaPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String keyTypeName = ((JavaPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldMapKeyTypeName();
		if (keyTypeName != null) {
			typeArgumentNames.add(keyTypeName);
		}
	}

	public String getMetamodelFieldMapKeyTypeName() {
		return MappingTools.getMetamodelFieldMapKeyTypeName(this);
	}
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.orderable.validate(messages, reporter, astRoot);
	}

}
