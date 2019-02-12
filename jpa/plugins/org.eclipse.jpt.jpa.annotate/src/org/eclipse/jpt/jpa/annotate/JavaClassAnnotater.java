/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate;

import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.jpa.annotate.mapping.AnnotationAttributeNames;
import org.eclipse.jpt.jpa.annotate.mapping.BasicEntityPropertyElem;
import org.eclipse.jpt.jpa.annotate.mapping.ColumnAttributes;
import org.eclipse.jpt.jpa.annotate.mapping.EntityMappingsDoc;
import org.eclipse.jpt.jpa.annotate.mapping.EntityPropertyElem;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.annotate.mapping.GeneratedValueAttributes;
import org.eclipse.jpt.jpa.annotate.mapping.IdEntityPropertyElement;
import org.eclipse.jpt.jpa.annotate.mapping.JoinStrategy;
import org.eclipse.jpt.jpa.annotate.mapping.JoinTableAttributes;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.ColumnMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.GeneratedValue;
import org.eclipse.jpt.jpa.core.context.GenerationType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneRelationship;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneRelationship;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;

public class JavaClassAnnotater 
{
	private PersistentType persistentType;
	private EntityMappingsDoc mappingDoc;
	private DatabaseAnnotationNameBuilder databaseAnnotationNameBuilder = DatabaseAnnotationNameBuilder.Default.INSTANCE;
	private Schema schema;
	private Table table;
	
	public JavaClassAnnotater(PersistentType persistentType, EntityMappingsDoc mappingDoc, Schema schema)
	{
		this.persistentType = persistentType;
		this.mappingDoc = mappingDoc;
		this.schema = schema;
		if (mappingDoc.getTableAttrs().getTableName() != null)
		{
			this.table = this.schema.getTableNamed(mappingDoc.getTableAttrs().getTableName());
		}
	}
	
	public void annotate(IProgressMonitor pm)
	{
		if (this.table != null)
		{
			SubMonitor sm = SubMonitor.convert(pm, mappingDoc.getEntityProperties().length + 1);
			sm.setTaskName(JptJpaAnnotateMessages.ANNOTATE_JAVA_CLASS);
			syncTableAnnotation();
			sm.worked(1);
			for (EntityPropertyElem entityPropertyElem : mappingDoc.getEntityProperties())
			{
				syncPropAnnotation(entityPropertyElem, entityPropertyElem.getPropertyName());
				sm.worked(1);
			}
		}
	}
	
	public DatabaseAnnotationNameBuilder getDatabaseAnnotationNameBuilder() 
	{
		return this.databaseAnnotationNameBuilder;
	}
	
	public void setDatabaseAnnotationNameBuilder(DatabaseAnnotationNameBuilder databaseAnnotationNameBuilder) 
	{
		if (databaseAnnotationNameBuilder == null) 
		{
			throw new NullPointerException("database annotation name builder is required");  //$NON-NLS-1$
		}
		this.databaseAnnotationNameBuilder = databaseAnnotationNameBuilder;
	}
	
	private void syncTableAnnotation() 
	{
		Entity entity = (Entity)this.persistentType.getMapping();
		String tableName = this.databaseAnnotationNameBuilder.buildTableAnnotationName(entity.getName(), table);
		if (tableName != null)
		{
			entity.getTable().setSpecifiedName(tableName);
		}
	}
	
	private void syncPropAnnotation(EntityPropertyElem propElem, String attributeName) 
	{
		SpecifiedPersistentAttribute persistentAttribute = (SpecifiedPersistentAttribute)this.persistentType.getAttributeNamed(attributeName);
		if (propElem == null || propElem.isUnmapped()) 
		{		
			persistentAttribute.setMappingKey(null);
			return;
		}
		String tagName = propElem.getTagName();
		if (tagName.equals(JPA.BASIC)) 
		{
			syncBasicAnnotation(propElem, persistentAttribute);
		} 
//		else if (tagName.equals(JPA.EMBEDDED_TAG)) 
//		{
//			_annotations.deleteClassMemberAnnotations(methName, propName, AnnotationNames.getPropTypeAnnotationNames(AnnotationNames.EMBEDDED_ANNOTATION));
//			_annotations.ensureClassMemberAnnotation(methName, propName, AnnotationNames.EMBEDDED_ANNOTATION);			
//		} 
//		else if (tagName.equals(JPA.EMBEDDED_ID_TAG)) 
//		{
//			_annotations.deleteClassMemberAnnotations(methName, propName, AnnotationNames.getPropTypeAnnotationNames(AnnotationNames.EMBEDDED_ID_ANNOTATION));
//			_annotations.ensureClassMemberAnnotation(methName, propName, AnnotationNames.EMBEDDED_ID_ANNOTATION);			
//		} 
		else if (tagName.equals(JPA.ID)) 
		{
			syncIdAnnotation(propElem, persistentAttribute);
		} 
		else if (tagName.equals(JPA.MANY_TO_MANY)) 
		{
			syncManyToManyAnnotation(propElem, persistentAttribute);
		} 
		else if (tagName.equals(JPA.MANY_TO_ONE)) 
		{
			syncManyToOneAnnotation(propElem, persistentAttribute);
		} 
		else if (tagName.equals(JPA.ONE_TO_MANY))
		{
			syncOneToManyAnnotation(propElem, persistentAttribute);
		} 
		else if (tagName.equals(JPA.ONE_TO_ONE)) 
		{
			syncOneToOneAnnotation(propElem, persistentAttribute);
		} 
//		else if (tagName.equals(JPA.VERSION_TAG)) 
//		{
//			syncVersionAnnotation(propElem, methName, propName);
//		}
	}
	
	private void syncBasicAnnotation(EntityPropertyElem propElem, SpecifiedPersistentAttribute persistentAttribute) 
	{
		/*remove the conflicting annotations*/
		// Since "Basic" is the default mapping, we can just clear the mapping annotation
		AttributeMapping mapping = persistentAttribute.setMappingKey(null);
		BasicMapping basicMapping = (BasicMapping)mapping;		
		assert propElem instanceof BasicEntityPropertyElem;
		BasicEntityPropertyElem basicElem = (BasicEntityPropertyElem)propElem;
		if (basicElem.getTemporalType() != null)
		{
			// TODO Set Temporal annotation
		}
				
		syncColumnAnnotation(propElem, basicMapping);
	}
		
	private void syncIdAnnotation(EntityPropertyElem propElem, SpecifiedPersistentAttribute persistentAttribute) 
	{
		AttributeMapping mapping = persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		IdMapping idMapping = (IdMapping) mapping;
		
		assert propElem instanceof IdEntityPropertyElement;
		IdEntityPropertyElement idElem = (IdEntityPropertyElement)propElem;
		GeneratedValueAttributes genAttrs = idElem.getGeneratedValueAttrs();
		if (genAttrs != null)
		{
			GeneratedValue gv = idMapping.addGeneratedValue();
			if (genAttrs.getStrategy() != null)
			{
				GenerationType type = getGenerationType(genAttrs.getStrategy());
				if (type != null && type != gv.getDefaultStrategy())
				{
					gv.setSpecifiedStrategy(type);
				}				
			}
			if (genAttrs.getGenerator() != null && genAttrs.getGenerator().equals(gv.getDefaultGenerator()))
			{
				gv.setSpecifiedGenerator(genAttrs.getGenerator());
			}
		}
		
		syncColumnAnnotation(propElem, idMapping);
	}
	
	private void syncColumnAnnotation(EntityPropertyElem propElem, ColumnMapping columnMapping) 
	{
		ColumnAttributes colAttrs = propElem.getColumnAnnotationAttrs();
		SpecifiedColumn column = columnMapping.getColumn();
		if (propElem.getDBColumn() != null)
		{
			Column dbColumn = propElem.getDBColumn();
			String colName = this.databaseAnnotationNameBuilder.buildColumnAnnotationName(propElem.getPropertyName(), dbColumn);
			// Setting col name, unique, nullable, length, precision, scale, insertable, updatable
			// only when it's not the default mapping.
			if (colName != null)
			{
				// Column Name
				column.setSpecifiedName(colName);
				// Unique
				if (colAttrs.isSetUnique() && colAttrs.isUnique())
				{
					column.setSpecifiedUnique(colAttrs.isUnique());
				}
				// Nullable
				if (colAttrs.isSetNullable() && !colAttrs.isNullable())
				{
					column.setSpecifiedNullable(colAttrs.isNullable());
				}
				//Length
				if (colAttrs.isSetLength() && colAttrs.getLength() != column.getDefaultLength())
				{
					column.setSpecifiedLength(colAttrs.getLength());
				}
				// Precision
				if (colAttrs.isSetPrecision() && colAttrs.getPrecision() != column.getDefaultPrecision())
				{
					column.setSpecifiedPrecision(colAttrs.getPrecision());
				}
				// Scale
				if (colAttrs.isSetScale() && colAttrs.getScale() != column.getDefaultScale())
				{
					column.setSpecifiedScale(colAttrs.getScale());
				}
				// Insertable
				if (colAttrs.isSetInsertable() && !colAttrs.isInsertable())
				{
					column.setSpecifiedInsertable(colAttrs.isInsertable());
				}
				// Updatable
				if (colAttrs.isSetUpdatable() && !colAttrs.isUpdatable())
				{
					column.setSpecifiedUpdatable(colAttrs.isUpdatable());
				}
			}
		}
		// ColumnDefinition
		if (colAttrs != null && colAttrs.getColumnDefinition() != null)
		{
			column.setColumnDefinition(colAttrs.getColumnDefinition());
		}
	}
	
	private void syncManyToOneAnnotation(EntityPropertyElem propElem, SpecifiedPersistentAttribute persistentAttribute) 
	{
		assert propElem instanceof EntityRefPropertyElem;
		EntityRefPropertyElem refPropElem = (EntityRefPropertyElem)propElem;

		AttributeMapping mapping = persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		ManyToOneMapping mtoMapping = (ManyToOneMapping)mapping;
		
		// Target Entity
		if (refPropElem.getRefEntityClassName() != null && 
				!refPropElem.getRefEntityClassName().equals(mtoMapping.getDefaultTargetEntity()))
		{
			mtoMapping.setSpecifiedTargetEntity(refPropElem.getRefEntityClassName());
		}
		
		// Fetch
		if (propElem.getAnnotationAttribute(AnnotationAttributeNames.FETCH) != null)
		{
			FetchType fetchType = getFetchType(propElem.getAnnotationAttribute(AnnotationAttributeNames.FETCH).attrValue);
			if (fetchType != null && fetchType != mtoMapping.getDefaultFetch())
			{
				mtoMapping.setSpecifiedFetch(fetchType);
			}
		}
		
		// Cascade
		syncCascadeMember(mtoMapping, refPropElem);		
				        
		if (!isJpa1_0Project() && refPropElem.getJoinTable() != null)
		{
			ManyToOneRelationship relation = mtoMapping.getRelationship();
			ManyToOneRelationship2_0 relation2 = (ManyToOneRelationship2_0)relation;
			relation2.setStrategyToJoinTable();
			SpecifiedJoinTableRelationshipStrategy joinTableStrategy = relation2.getJoinTableStrategy();
			syncJoinTableAnnotation(refPropElem, joinTableStrategy);
		}
		else if (!refPropElem.getJoinColumns().isEmpty())
		{
			mtoMapping.getRelationship().setStrategyToJoinColumn();
			SpecifiedJoinColumnRelationshipStrategy joinColumnStrategy = mtoMapping.getRelationship().getJoinColumnStrategy();
			syncJoinColumnsAnnotations(refPropElem.getDBTable(), refPropElem.getReferencedTable(),
					refPropElem.getPropertyName(), refPropElem.getJoinColumns(), joinColumnStrategy);
		}		
	}
	
	private void syncOneToManyAnnotation(EntityPropertyElem propElem, SpecifiedPersistentAttribute persistentAttribute) 
	{
		assert propElem instanceof EntityRefPropertyElem;
		EntityRefPropertyElem refPropElem = (EntityRefPropertyElem)propElem;
		
		AttributeMapping mapping = persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping otmMapping = (OneToManyMapping)mapping;
		
		// Target Entity
		if (refPropElem.getRefEntityClassName() != null && 
				!refPropElem.getRefEntityClassName().equals(otmMapping.getDefaultTargetEntity()))
		{
			otmMapping.setSpecifiedTargetEntity(refPropElem.getRefEntityClassName());
		}
		
		// Fetch
		if (propElem.getAnnotationAttribute(AnnotationAttributeNames.FETCH) != null)
		{
			FetchType fetchType = getFetchType(propElem.getAnnotationAttribute(AnnotationAttributeNames.FETCH).attrValue);
			if (fetchType != null && fetchType != otmMapping.getDefaultFetch())
			{
				otmMapping.setSpecifiedFetch(fetchType);
			}
		}
		
		// Cascade
		syncCascadeMember(otmMapping, refPropElem);	
		
		syncToManyAnnotations(propElem, otmMapping);
				
		if (propElem.getAnnotationAttribute(AnnotationAttributeNames.MAPPEDBY) != null)
		{
			// Mapped by
			otmMapping.getRelationship().setStrategyToMappedBy();
			SpecifiedMappedByRelationshipStrategy mappedByStrategy = otmMapping.getRelationship().getMappedByStrategy();
			mappedByStrategy.setMappedByAttribute(propElem.getAnnotationAttribute(AnnotationAttributeNames.MAPPEDBY).attrValue);
		}
		else if (refPropElem.getJoinTable() != null)
		{
			// Join Table
			otmMapping.getRelationship().setStrategyToJoinTable();
			SpecifiedJoinTableRelationshipStrategy joinTableStrategy = otmMapping.getRelationship().getJoinTableStrategy();
			syncJoinTableAnnotation(refPropElem, joinTableStrategy);
		}
		else if (!isJpa1_0Project() && !refPropElem.getJoinColumns().isEmpty())
		{
			// Join columns
			SpecifiedJoinColumnRelationshipStrategy joinColumnStrategy = null;
			if (otmMapping.getRelationship() instanceof OneToManyRelationship2_0)
			{
				OneToManyRelationship2_0 relation2_0 = (OneToManyRelationship2_0)otmMapping.getRelationship();
				relation2_0.setStrategyToJoinColumn();
				joinColumnStrategy = relation2_0.getJoinColumnStrategy();
			}
			if (joinColumnStrategy != null)
			{
				syncJoinColumnsAnnotations(refPropElem.getDBTable(), refPropElem.getReferencedTable(),
						refPropElem.getPropertyName(), refPropElem.getJoinColumns(), joinColumnStrategy);
			}
		}
	}
	
	private void syncOneToOneAnnotation(EntityPropertyElem propElem, SpecifiedPersistentAttribute persistentAttribute) 
	{
		assert propElem instanceof EntityRefPropertyElem;
		EntityRefPropertyElem refPropElem = (EntityRefPropertyElem)propElem;
		AttributeMapping mapping = persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OneToOneMapping otoMapping = (OneToOneMapping)mapping;
		
		// Target Entity
		if (refPropElem.getRefEntityClassName() != null && 
				!refPropElem.getRefEntityClassName().equals(otoMapping.getDefaultTargetEntity()))
		{
			otoMapping.setSpecifiedTargetEntity(refPropElem.getRefEntityClassName());
		}
		
		// Fetch
		if (propElem.getAnnotationAttribute(AnnotationAttributeNames.FETCH) != null)
		{
			FetchType fetchType = getFetchType(propElem.getAnnotationAttribute(AnnotationAttributeNames.FETCH).attrValue);
			if (fetchType != null && fetchType != otoMapping.getDefaultFetch())
			{
				otoMapping.setSpecifiedFetch(fetchType);
			}
		}
		
		// Cascade
		syncCascadeMember(otoMapping, refPropElem);		

		if (propElem.getAnnotationAttribute(AnnotationAttributeNames.MAPPEDBY) != null)
		{
			// Mapped by
			otoMapping.getRelationship().setStrategyToMappedBy();
			SpecifiedMappedByRelationshipStrategy mappedByStrategy = otoMapping.getRelationship().getMappedByStrategy();
			mappedByStrategy.setMappedByAttribute(propElem.getAnnotationAttribute(AnnotationAttributeNames.MAPPEDBY).attrValue);
		}		
		else if (!refPropElem.getJoinColumns().isEmpty())
		{
			// Join columns			
			otoMapping.getRelationship().setStrategyToJoinColumn();
			SpecifiedJoinColumnRelationshipStrategy joinColumnStrategy = otoMapping.getRelationship().getJoinColumnStrategy();
			syncJoinColumnsAnnotations(refPropElem.getDBTable(), refPropElem.getReferencedTable(),
					refPropElem.getPropertyName(), refPropElem.getJoinColumns(), joinColumnStrategy);
		}
		else if (refPropElem.getJoinStrategy() == JoinStrategy.PRIMARY_KEY_JOIN_COLUMNS)
		{
			// Primary Key Join Columns
			otoMapping.getRelationship().setStrategyToPrimaryKeyJoinColumn();
			SpecifiedPrimaryKeyJoinColumnRelationshipStrategy pkJoinStrategy = otoMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
			syncPkJoinColumnAnnotations(refPropElem.getPkJoinColumns(), pkJoinStrategy);
		}
		else if (!isJpa1_0Project() && refPropElem.getJoinTable() != null)
		{
			// Join table
			OneToOneRelationship relation = otoMapping.getRelationship();
			OneToOneRelationship2_0 relation2 = (OneToOneRelationship2_0)relation;
			relation2.setStrategyToJoinTable();
			SpecifiedJoinTableRelationshipStrategy joinTableStrategy = relation2.getJoinTableStrategy();
			syncJoinTableAnnotation(refPropElem, joinTableStrategy);
		}
	}
	
	private void syncManyToManyAnnotation(EntityPropertyElem propElem, SpecifiedPersistentAttribute persistentAttribute) 
	{
		assert propElem instanceof EntityRefPropertyElem;
		EntityRefPropertyElem refPropElem = (EntityRefPropertyElem)propElem;		
		AttributeMapping mapping = persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		ManyToManyMapping mtmMapping = (ManyToManyMapping)mapping;
		
		// Target Entity
		if (refPropElem.getRefEntityClassName() != null && 
				!refPropElem.getRefEntityClassName().equals(mtmMapping.getDefaultTargetEntity()))
		{
			mtmMapping.setSpecifiedTargetEntity(refPropElem.getRefEntityClassName());
		}
		
		// Fetch
		if (propElem.getAnnotationAttribute(AnnotationAttributeNames.FETCH) != null)
		{
			FetchType fetchType = getFetchType(propElem.getAnnotationAttribute(AnnotationAttributeNames.FETCH).attrValue);
			if (fetchType != null && fetchType != mtmMapping.getDefaultFetch())
			{
				mtmMapping.setSpecifiedFetch(fetchType);
			}
		}
		
		// Cascade
		syncCascadeMember(mtmMapping, refPropElem);		
		
		syncToManyAnnotations(propElem, mtmMapping);
		
		// Join Strategy
		if (refPropElem.getAnnotationAttribute(AnnotationAttributeNames.MAPPEDBY) != null)
		{
			// Mapped By
			mtmMapping.getRelationship().setStrategyToMappedBy();
			SpecifiedMappedByRelationshipStrategy mappedByStrategy = mtmMapping.getRelationship().getMappedByStrategy();
			mappedByStrategy.setMappedByAttribute(propElem.getAnnotationAttribute(AnnotationAttributeNames.MAPPEDBY).attrValue);
		}
		else if (refPropElem.getJoinTable() != null)
		{
			// Join table
			mtmMapping.getRelationship().setStrategyToJoinTable();
			SpecifiedJoinTableRelationshipStrategy joinTableStrategy = mtmMapping.getRelationship().getJoinTableStrategy();
			syncJoinTableAnnotation(refPropElem, joinTableStrategy);
		}		
		
	}
	
	private void syncCascadeMember(RelationshipMapping relationMapping, EntityRefPropertyElem refPropElem) 
	{
		Cascade cascade = relationMapping.getCascade();
		// Clear the exising cascade
		cascade.setAll(false);
		cascade.setMerge(false);
		cascade.setPersist(false);
		cascade.setRefresh(false);
		cascade.setRemove(false);

		List<String> cascadeStrs = refPropElem.getAllCascades();
		for (String cascadeStr : cascadeStrs)
		{
			if (cascadeStr.equals(JPA.CASCADE_ALL)) 
			{
				cascade.setAll(true);
			}
			if (cascadeStr.equals(JPA.CASCADE_PERSIST)) 
			{
				cascade.setPersist(true);
			}
			if (cascadeStr.equals(JPA.CASCADE_MERGE)) 
			{
				cascade.setMerge(true);
			}
			if (cascadeStr.equals(JPA.CASCADE_REMOVE)) 
			{
				cascade.setRemove(true);
			}
			if (cascadeStr.equals(JPA.CASCADE_REFRESH)) 
			{
				cascade.setRefresh(true);
			}
		}
	}
	
	private void syncJoinTableAnnotation(EntityRefPropertyElem refPropEmElem, SpecifiedJoinTableRelationshipStrategy jtRelation) 
	{
		JoinTableAttributes joinTableAttrs = refPropEmElem.getJoinTable();
		assert joinTableAttrs != null;
		SpecifiedJoinTable joinTable = jtRelation.getJoinTable();
		Table dbJoinTable = null;
		// Name
		if (joinTableAttrs.getTableName() != null)
		{
			dbJoinTable = this.schema.getTableNamed(joinTableAttrs.getTableName());
			if (dbJoinTable != null)
			{
				String joinTableName = this.databaseAnnotationNameBuilder.buildJoinTableAnnotationName(dbJoinTable);
				joinTable.setSpecifiedName(joinTableName != null ? joinTableName : dbJoinTable.getName());
			}
		}
		
		List<ColumnAttributes> cols = joinTableAttrs.getJoinColumns();
		for (ColumnAttributes col : cols)
		{
			SpecifiedJoinColumn joinColumn = joinTable.addSpecifiedJoinColumn();
			
			syncJoinColumnAnnotations(true, dbJoinTable, refPropEmElem.getDBTable(), 
					refPropEmElem.getPropertyName(), col, joinColumn);
		}
		List<ColumnAttributes> inverseCols = joinTableAttrs.getInverseJoinColumns();
		for (ColumnAttributes col : inverseCols)
		{
			SpecifiedJoinColumn joinColumn = joinTable.addSpecifiedInverseJoinColumn();
			syncJoinColumnAnnotations(true, dbJoinTable, refPropEmElem.getReferencedTable(), refPropEmElem.getPropertyName(),
					col, joinColumn);
		}
	}
	
	private void syncJoinColumnAnnotations(boolean isJoinTable, Table baseTable, Table refTable, String propName, 
			ColumnAttributes col, SpecifiedJoinColumn joinColumn)
	{
		if (baseTable == null || refTable == null)
		{
			return;
		}
		boolean printoutNameOrRefName = false;
		if (col.getName() != null)
		{
			Column dbColumn = baseTable.getColumnNamed(col.getName());
			ForeignKey fk = getForeignKey(dbColumn);
			Column refDbCol = col.getReferencedColumnName() != null ? 
					refTable.getColumnNamed(col.getReferencedColumnName()) : null;

			if ((fk != null && !fk.referencesSingleColumnPrimaryKey()) || isJoinTable)
			{
				if (refDbCol != null)
				{
					// need to print out both columnName and referenceColumnName
					String colName = this.databaseAnnotationNameBuilder.buildJoinColumnAnnotationName(dbColumn);
					String refColName = this.databaseAnnotationNameBuilder.buildJoinColumnAnnotationName(refDbCol);
					joinColumn.setSpecifiedName(colName);
					joinColumn.setSpecifiedReferencedColumnName(refColName);
					printoutNameOrRefName = true;
				}				
			}
			else
			{
				// No need to print out referenceColumnName. Print out columnName if 
				// the name cannot be defaulted
				String colName = this.databaseAnnotationNameBuilder.buildColumnAnnotationName(propName, dbColumn);
				if (colName != null)
				{
					joinColumn.setSpecifiedName(colName);
					printoutNameOrRefName = true;
				}
			}				
		}
		if (printoutNameOrRefName)
		{
			// Unique
			if (col.isSetUnique() && col.isUnique())
			{
				joinColumn.setSpecifiedUnique(col.isUnique());
			}
			// Nullable
			if (col.isSetNullable() && !col.isNullable())
			{
				joinColumn.setSpecifiedNullable(col.isNullable());
			}
			// Insertable
			if (col.isSetInsertable() && !col.isInsertable())
			{
				joinColumn.setSpecifiedInsertable(col.isInsertable());
			}
			// Updatable
			if (col.isSetUpdatable() && !col.isUpdatable())
			{
				joinColumn.setSpecifiedUpdatable(col.isUpdatable());
			}
			// Column Definition
			if (col.getColumnDefinition() != null)
			{
				joinColumn.setColumnDefinition(col.getColumnDefinition());
			}
		}
	}
	
	private void syncJoinColumnsAnnotations(Table baseTable, Table refTable, String propertyName, List<ColumnAttributes> joinColumns, SpecifiedJoinColumnRelationshipStrategy joinColumnStrategy) 
	{		
		if (joinColumns == null || joinColumns.size() == 0) 
		{
			return;
		}
		int count = joinColumns.size();
		/*remove empty join column tags so that no annotation is generated for them*/
		for (int i = count-1; i >= 0; --i) 
		{
			if (joinColumns.get(i).isEmpty()) 
			{
				joinColumns.remove(i);
			}
		}
		count = joinColumns.size(); //recompute the size just in case any tag has been removed from the list above
		
		for (int i = 0; i < count; ++i)
		{
			ColumnAttributes col = joinColumns.get(i);
			SpecifiedJoinColumn joinColumn = joinColumnStrategy.addSpecifiedJoinColumn();
			syncJoinColumnAnnotations(false, baseTable, refTable, propertyName, col, joinColumn);
		}
	}
	
	/**
	 * Sync the MapBy and OrderBy annotations.
	 */
	private void syncToManyAnnotations(EntityPropertyElem propElem,	CollectionMapping collectionMapping) 
	{
		assert propElem instanceof EntityPropertyElem;
		EntityRefPropertyElem refPropElem = (EntityRefPropertyElem)propElem;
		// TODO @MapKey
		
		// Order by
		if (refPropElem.getOrderBy() != null) {
			collectionMapping.getOrderable().setOrderByOrdering();
			collectionMapping.getOrderable().getOrderBy().setKey(refPropElem.getOrderBy());
		}
	}
	
	private void syncPkJoinColumnAnnotations(List<ColumnAttributes> pkJoinCols, SpecifiedPrimaryKeyJoinColumnRelationshipStrategy pkJoinStrategy) 
	{
		for (ColumnAttributes pkJoinCol : pkJoinCols) 
		{
			SpecifiedPrimaryKeyJoinColumn pkJoinColumn = pkJoinStrategy.addPrimaryKeyJoinColumn();
			// Name
			if (pkJoinCol.getName() != null && !pkJoinCol.getName().equals(pkJoinColumn.getDefaultName()))
			{
				pkJoinColumn.setSpecifiedName(pkJoinCol.getName());
			}
			// Referenced Column Name
			if (pkJoinCol.getReferencedColumnName() != null && 
					!pkJoinCol.getReferencedColumnName().equals(pkJoinColumn.getDefaultReferencedColumnName()))
			{
				pkJoinColumn.setSpecifiedReferencedColumnName(pkJoinCol.getReferencedColumnName());
			}
			// Column Definition
			if (pkJoinCol.getColumnDefinition() != null)
			{
				pkJoinColumn.setColumnDefinition(pkJoinCol.getColumnDefinition());
			}			
		}
	}
	
	private GenerationType getGenerationType(String strategy)
	{
		if (strategy.equalsIgnoreCase("auto"))
		{
			return GenerationType.AUTO;
		}
		else if (strategy.equalsIgnoreCase("identity"))
		{
			return GenerationType.IDENTITY;
		}
		else if (strategy.equalsIgnoreCase("sequence"))
		{
			return GenerationType.SEQUENCE;
		}
		else if (strategy.equalsIgnoreCase("table"))
		{
			return GenerationType.TABLE;
		}
		return null;
	}
	
	private FetchType getFetchType(String fetch)
	{
		if (fetch == null)
		{
			return null;
		}
		else if (fetch.equalsIgnoreCase("eager"))
		{
			return FetchType.EAGER;
		}
		else if (fetch.equalsIgnoreCase("lazy"))
		{
			return FetchType.LAZY;
		}
		else 
		{
			return null;
		}
	}
	
	private boolean isJpa1_0Project()
	{
        return this.persistentType.getPersistenceUnit().getJpaPlatform().getJpaVersion().getVersion().equals("1.0");
	}
	
	private ForeignKey getForeignKey(Column dbColumn)
	{
		if( dbColumn== null || !dbColumn.isPartOfForeignKey())
		{
			return null;
		}
		ForeignKey foreignKey = null;
		Iterable<ForeignKey> it = dbColumn.getTable().getForeignKeys();
		Iterator<ForeignKey> i = it.iterator();
		while( i.hasNext() )
		{
			ForeignKey fk = i.next();
			Column c = fk.getBaseColumns().iterator().next();
			if( c.equals( dbColumn ) )
			{
				foreignKey = fk;
				break;
			}
		}
		return foreignKey;
	}
			
}
