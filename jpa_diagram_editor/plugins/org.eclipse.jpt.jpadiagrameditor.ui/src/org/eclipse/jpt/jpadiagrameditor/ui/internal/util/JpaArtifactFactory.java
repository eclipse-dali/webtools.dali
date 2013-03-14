/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandContext;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.ColumnMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingRelationship;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsIdAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.AddAttributeCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.AddPersistentTypeToOrmXmlCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.CreateEntityTypeHierarchy;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.DeleteAttributeCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.RenameAttributeCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.RenameEntityCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.SetMappedByNewValueCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddHasReferenceRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddInheritedEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.UpdateAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasCollectionReferenceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation.HasReferenceType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasSingleReferenceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IBidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelDir;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IUnidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IsARelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToOneBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToOneUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneUniDirRelation;
import org.eclipse.swt.widgets.Display;


@SuppressWarnings("restriction")
public class JpaArtifactFactory {

	private static final JpaArtifactFactory INSTANCE = new JpaArtifactFactory();
	
	private static final int MAX_NUM_OF_ITERATIONS = 25;
	private static final int PAUSE_DURATION = 200;

	synchronized public static JpaArtifactFactory instance() {
		return INSTANCE;
	}
	
	public void rearrangeIsARelations(IJPAEditorFeatureProvider fp) {
		Collection<IsARelation> isARels = produceAllMissingIsARelations(fp);
		addIsARelations(fp, isARels);
		fp.removeAllRedundantIsARelations();
	} 
	
	public void rearrangeIsARelationsInTransaction(final IJPAEditorFeatureProvider fp) {
		final Collection<IsARelation> isARels = produceAllMissingIsARelations(fp);
		if (!fp.existRedundantIsARelations() && (isARels.size() == 0))
			return;
		TransactionalEditingDomain ted = fp.getTransactionalEditingDomain();
		if(ted == null)
			return;
		RecordingCommand rc = new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				addIsARelations(fp, isARels);
				fp.removeAllRedundantIsARelations();
			}		
		};
		ted.getCommandStack().execute(rc);
	}
		
	public Collection<IsARelation> produceAllMissingIsARelations(IJPAEditorFeatureProvider fp) {
		Collection<PersistentType> persistentTypes = fp.getPersistentTypes();
		Collection<IsARelation> res = new HashSet<IsARelation>();
		Iterator<PersistentType> it = persistentTypes.iterator(); 
		HashSet<IsARelation> allExistingIsARelations = fp.getAllExistingIsARelations();
		while (it.hasNext()) {
			PersistentType jpt = it.next();
			PersistentType superclass = fp.getFirstSuperclassBelongingToTheDiagram(jpt);
			if (superclass == null)
				continue;
			IsARelation newRel = new IsARelation(jpt, superclass);
			if (!allExistingIsARelations.contains(newRel))
				res.add(newRel);
		}
		return res;
	}

	public void addOneToOneUnidirectionalRelation(IFeatureProvider fp, PersistentType jpt, 
												  PersistentAttribute attribute) {
		addOneToOneRelation(fp, jpt, attribute, null, null,
				JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL);
	}
	
	public void addOneToOneBidirectionalRelation (IFeatureProvider fp, PersistentType jpt1, 
			PersistentAttribute attribute1, PersistentType jpt2,
												  PersistentAttribute attribute2) {		
		addOneToOneRelation(fp, jpt1, attribute1, jpt2, attribute2,
				JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL);
	}

	public void addOneToManyUnidirectionalRelation(IFeatureProvider fp, PersistentType jpt, 
												   PersistentAttribute attribute, boolean isMap) {		
		addOneToManyRelation(fp, jpt, attribute, null, null,
				JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL, isMap);
	}
	
	public void addOneToManyBidirectionalRelation(IFeatureProvider fp, PersistentType jpt1, 
			PersistentAttribute attribute1, PersistentType jpt2,
												  PersistentAttribute attribute2, boolean isMap) {		
		addOneToManyRelation(fp, jpt1, attribute1, jpt2, attribute2,
				JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL, isMap);
	}

	public void addManyToOneBidirectionalRelation(IFeatureProvider fp, PersistentType jpt1, 
			PersistentAttribute attribute1, PersistentType jpt2,
												  PersistentAttribute attribute2, boolean isMap) {
		
		addManyToOneRelation(fp, jpt1, attribute1, jpt2, attribute2,
				JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL, isMap);
	}
	
	public void addOneToOneRelation(IFeatureProvider fp,
			PersistentType ownerJPT, PersistentAttribute ownerAttibute,
			PersistentType referencedJPT,
			PersistentAttribute referencedAttribute, int direction) {
		
		setMappingKeyToAttribute(fp, ownerJPT, ownerAttibute, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {
			PersistentAttribute resolvedAttribute = setMappingKeyToAttribute(fp, referencedJPT, referencedAttribute, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);			
			String mappedByAttr = getMappeByAttribute(fp, ownerJPT, ownerAttibute);
			setMappedByAttribute(resolvedAttribute, referencedJPT, mappedByAttr);
		}

	}

	public void addManyToOneRelation(IFeatureProvider fp, PersistentType manySideJPT,
			PersistentAttribute manySideAttribute, PersistentType singleSideJPT,
			PersistentAttribute singleSideAttibute, int direction, boolean isMap) {
		
		setMappingKeyToAttribute(fp, manySideJPT, manySideAttribute, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);

		if (direction == JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL)
			return;

		PersistentAttribute resolvedSingleSideAttribute = setMappingKeyToAttribute(fp, singleSideJPT, singleSideAttibute, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		String mappedByAttr = getMappeByAttribute(fp, manySideJPT, manySideAttribute);
		setMappedByAttribute(resolvedSingleSideAttribute, singleSideJPT, mappedByAttr);
		if (isMap) {
			if(getORMPersistentAttribute(singleSideAttibute) == null) { 		
				singleSideAttibute.getJavaPersistentAttribute().getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
			}
		}
	}

	public void addOneToManyRelation(IFeatureProvider fp, PersistentType singleSideJPT, 
									 PersistentAttribute singleSideAttibute, 
									 PersistentType manySideJPT, 
			PersistentAttribute manySideAttribute, int direction, boolean isMap) {

		PersistentAttribute resolvedSingleSideAttribute = setMappingKeyToAttribute(fp, singleSideJPT, singleSideAttibute, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {		
			setMappingKeyToAttribute(fp, manySideJPT, manySideAttribute, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			String mappedByAttr = getMappeByAttribute(fp, manySideJPT, manySideAttribute);
			setMappedByAttribute(resolvedSingleSideAttribute, singleSideJPT, mappedByAttr);			
		} else {
			addJoinColumnIfNecessary(resolvedSingleSideAttribute, singleSideJPT);
		}		
		if (isMap) {
			if(getORMPersistentAttribute(singleSideAttibute) == null) { 		
				singleSideAttibute.getJavaPersistentAttribute().getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
			}
		}
	}

	private String getMappeByAttribute(IFeatureProvider fp,
			PersistentType ownerSideJPT, PersistentAttribute ownerSideAttribute) {		
		String mappedByAttr = ownerSideAttribute.getName();
		
		if(isEmbeddable(ownerSideJPT)){
			HasReferanceRelation ref = JpaArtifactFactory.INSTANCE.findFisrtHasReferenceRelationByEmbeddable(ownerSideJPT, (IJPAEditorFeatureProvider)fp);
			if(ref != null){
				PersistentAttribute embeddingAttribute = ref.getEmbeddedAnnotatedAttribute();
				mappedByAttr  = embeddingAttribute.getName() + "." + ownerSideAttribute.getName(); //$NON-NLS-1$
			}
		}
		return mappedByAttr;
	}

	private void setMappedByAttribute(PersistentAttribute resolvedAttr, PersistentType type1, String jpaName){
		setMappedByRelationAttribute(resolvedAttr, jpaName);
		PersistentAttribute ormAttribute = getORMPersistentAttribute(resolvedAttr);
		if(ormAttribute != null){
			 setMappedByRelationAttribute(ormAttribute, jpaName);
		}
	}

	private void setMappedByRelationAttribute(
			PersistentAttribute resolvedAttr, String jpaName) {
		AttributeMapping mapping = resolvedAttr.getMapping();
		if(!(mapping instanceof RelationshipMapping))
			return;
		RelationshipMapping relationshipMappin = (RelationshipMapping) resolvedAttr.getMapping();
		MappedByRelationship mappedByRel = (MappedByRelationship) relationshipMappin.getRelationship();
		SpecifiedMappedByRelationshipStrategy mappedByStrategy = mappedByRel.getMappedByStrategy();
		mappedByStrategy.setMappedByAttribute(jpaName);
	}

	private PersistentAttribute setMappingKeyToAttribute(IFeatureProvider fp, PersistentType jpt, PersistentAttribute jpa, String mappingKey){
		PersistentAttribute resolvedManySideAttribute = jpt.resolveAttribute(jpa.getName());
		resolvedManySideAttribute.getJavaPersistentAttribute().setMappingKey(mappingKey);
		addOrmPersistentAttribute(jpt, jpa, mappingKey);
		return resolvedManySideAttribute;
	}

	private void addJoinColumnIfNecessary(PersistentAttribute jpa,
			PersistentType jpt) {

		if (JPAEditorUtil.checkJPAFacetVersion(jpa.getJpaProject(), JPAEditorUtil.JPA_PROJECT_FACET_10) ||
				JPADiagramPropertyPage.shouldOneToManyUnidirBeOldStyle(jpa
						.getJpaProject().getProject()))
			return;

		PersistentAttribute[] ids = getIds(jpt);
		if (ids.length == 0)
			return;
		
		SpecifiedJoinColumnRelationshipStrategy strategy = getJoinColumnStrategy(jpa);
		if(strategy == null)
			return;

		final String tableName = getTableName(jpt);

		if (ids.length == 1) {
			if (isSimpleId(ids[0])) {
				String idColName = getColumnName(ids[0]);
				addJoinColumn(jpa, tableName, strategy, idColName);
			} else {
				Hashtable<String, String> atNameToColName = getOverriddenColNames(ids[0]);
				PersistenceUnit pu = getPersistenceUnit(jpt);
				String embeddableTypeName = getRelTypeName(ids[0]);
				Embeddable emb = pu.getEmbeddable(embeddableTypeName);
				for (AttributeMapping am : emb.getAllAttributeMappings()) {
					PersistentAttribute at = am.getPersistentAttribute();
					String idColName = atNameToColName.get(at.getName());
					idColName = (idColName != null) ? idColName	: getColumnName(at);
					addJoinColumn(jpa, tableName, strategy, idColName);
				}
			}
		} else {
			for (PersistentAttribute idAt : ids) {
				String idColName = getColumnName(idAt);
				addJoinColumn(jpa, tableName, strategy, idColName);
			}
		}
	}
	
	private SpecifiedJoinColumnRelationshipStrategy getJoinColumnStrategy(PersistentAttribute jpa){
		AttributeMapping attributeMapping = getAttributeMapping(jpa);
		if(attributeMapping instanceof RelationshipMapping) {
			RelationshipStrategy strategy = ((RelationshipMapping) attributeMapping).getRelationship().getStrategy();
			if(strategy instanceof SpecifiedJoinColumnRelationshipStrategy){
				return (SpecifiedJoinColumnRelationshipStrategy) strategy;
			}
		}
		return null;
	}

	private void addJoinColumn(PersistentAttribute jpa,
			final String tableName, SpecifiedJoinColumnRelationshipStrategy strategy,
			String idColName) {
		String name = tableName + "_" + idColName; //$NON-NLS-1$

		SpecifiedJoinColumn joinColumn = strategy.addSpecifiedJoinColumn();
		joinColumn.setSpecifiedName(name);
		joinColumn.setSpecifiedReferencedColumnName(idColName);
	}

	@SuppressWarnings("unchecked")
	private Hashtable<String, String> getOverriddenColNames(PersistentAttribute embIdAt) {
		AttributeMapping attributeMapping = getAttributeMapping(embIdAt);
		Hashtable<String, String> res = new Hashtable<String, String>();
		if(attributeMapping instanceof EmbeddedIdMapping){
			AttributeOverrideContainer attributeOverrideContainer = ((EmbeddedIdMapping)attributeMapping).getAttributeOverrideContainer();
			if(attributeOverrideContainer.getSpecifiedOverridesSize() > 0) {
				Iterator<AttributeOverride> overridenAttributesIterator = (Iterator<AttributeOverride>) attributeOverrideContainer.getSpecifiedOverrides().iterator();
				while(overridenAttributesIterator.hasNext()){
					AttributeOverride attributeOverride = overridenAttributesIterator.next();
					res.put(attributeOverride.getName(), attributeOverride.getColumn().getName());
				}
			}
		}		
		return res;
	}

	public void addManyToOneUnidirectionalRelation(IFeatureProvider fp, PersistentType jpt, 
												   PersistentAttribute attribute) {
		
		addManyToOneRelation(fp, jpt, attribute, null, null,
				JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL, false);
	}

	public void addManyToManyBidirectionalRelation(IFeatureProvider fp, PersistentType jpt1, 
			PersistentAttribute attribute1, PersistentType jpt2,
												   PersistentAttribute attribute2, boolean isMap) {
		
		addManyToManyRelation(fp, jpt1, attribute1, jpt2, attribute2,
				JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL, isMap);
	}

	public void addManyToManyUnidirectionalRelation(IFeatureProvider fp, PersistentType annotatedJPT,
													PersistentAttribute annotatedAttribute, boolean isMap) {
		
		addManyToManyRelation(fp, annotatedJPT, annotatedAttribute, null, null,
				JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL, isMap);
	}

	public void addManyToManyRelation(IFeatureProvider fp, PersistentType ownerSideJPT, 
									  PersistentAttribute ownerSideAttribute, 
									  PersistentType inverseSideJPT, 
			PersistentAttribute inverseSideAttibute, int direction, boolean isMap) {		

		PersistentAttribute resolvedOwnerSideAttribute = setMappingKeyToAttribute(fp, ownerSideJPT, ownerSideAttribute, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		if (isMap) {
			if(getORMPersistentAttribute(resolvedOwnerSideAttribute) == null) {
				resolvedOwnerSideAttribute.getJavaPersistentAttribute().getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
			}
		}
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {
			PersistentAttribute resolvedInverseSideAttribute = setMappingKeyToAttribute(fp, inverseSideJPT, inverseSideAttibute, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			String mappedByAttr = getMappeByAttribute(fp, ownerSideJPT, ownerSideAttribute);
			setMappedByAttribute(resolvedInverseSideAttribute, inverseSideJPT, mappedByAttr);
			
			if (isMap) {
				if(getORMPersistentAttribute(resolvedInverseSideAttribute) == null) {
					resolvedInverseSideAttribute.getJavaPersistentAttribute().getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
				}
			}
		}
		
	}
		
	public void restoreEntityClass(PersistentType jpt, IJPAEditorFeatureProvider fp) {
		fp.restoreEntity(jpt);
	}    	
	
	public void forceSaveEntityClass(final PersistentType jpt,
			IJPAEditorFeatureProvider fp) {
		final ICompilationUnit cu = fp.getCompilationUnit(jpt);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					if (cu.isWorkingCopy()) 
						cu.commitWorkingCopy(true, new NullProgressMonitor());
					cu.save(new NullProgressMonitor(), true);
				} catch (JavaModelException e) {
					if (cu.getResource().getProject().isAccessible() && cu.getResource().isAccessible())
						JPADiagramEditorPlugin.logError("Cannot save entity '" + jpt.getName() + "'", e);	//$NON-NLS-1$		//$NON-NLS-2$		
				}
			}
		});
	}
	
	public boolean deleteEntityClass(PersistentType jpt, 
									 IJPAEditorFeatureProvider fp) {
		ICompilationUnit cu = fp.getCompilationUnit(jpt);
		try {
			JPAEditorUtil.discardWorkingCopy(cu);
			cu.delete(true, new NullProgressMonitor());
			return true;			
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannot delete the JPA entity class", e); //$NON-NLS-1$				
			return false;
		}
	} 
	   
	public void deletePersistenceTypeResource(PersistentType type)
			throws CoreException {
		JpaProject proj = type.getJpaProject();
		String typeName = type.getName();
		IResource entityResource = type.getResource();
		//type.dispose();
		entityResource.delete(true, new NullProgressMonitor());	
		int cnt = 0;
		PersistentType jpt = getContextPersistentType(proj, typeName);
		while ((jpt != null) && (cnt < MAX_NUM_OF_ITERATIONS)) {
			try {
				Thread.sleep(PAUSE_DURATION);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Sleep interrupted", e); //$NON-NLS-1$				
			}
			cnt++;
		}
	}

	public PersistentType getContextPersistentType(JpaProject jpaProject, 
													   String fullyQualifiedTypeName) {
		PersistenceUnit unit = getPersistenceUnit(jpaProject);
		for (ClassRef ref : unit.getClassRefs()) {
			PersistentType jpt = ref.getJavaPersistentType(); 
			if ((jpt != null) && jpt.getName().equals(fullyQualifiedTypeName) ) {
				return ref.getJavaPersistentType();
			}
		}

		return getOrmXmlPersistentTypeByName(jpaProject, fullyQualifiedTypeName);
	}
	
	/*
	 * Return name of the entity from @Entity(name="...").
	 * If there is no such annotation, returns the 
	 * fully qualified name of the class
	 */
	public String getEntityName(PersistentType jpt) {
		if (jpt == null)
			return "";	//$NON-NLS-1$
		JavaResourceType jrpt = jpt.getJavaResourceType();
		if (jrpt == null) 
			return "";	//$NON-NLS-1$
		
		String name = null;
		TypeMapping mapping = jpt.getMapping();
		if ((mapping != null) && Entity.class.isInstance(mapping))
			name = ((Entity)mapping).getSpecifiedName();
		if (name == null)
			name = jpt.getName();
		return name;
	}
								
	public boolean hasEntitySpecifiedName(PersistentType jpt) {
		if (jpt == null)
			return false;
		TypeMapping jtm = getTypeMapping(jpt);
		if (jtm == null)
			return false;
		if (jtm instanceof Entity) {
			Entity mapping = (Entity)jtm;
			return (mapping.getSpecifiedName() != null);
		}
		return false;
	}
	
	/**
	 * Checks whether the the persistent type has any of the following annotations:
	 * Entity, Embeddable or MappedSuperclass
	 * @param jpt
	 * @return true, if the given jpt is annotated, false otherwise.
	 */
	public boolean isAnyKindPersistentType(PersistentType jpt) {
		return isEntity(jpt) || isMappedSuperclass(jpt) || isEmbeddable(jpt); 
	}
	
	/**
	 * Checks whether the persistent type is of entity type
	 * @param jpt
	 * @return true, if the given jpt is an entity, false otherwise
	 */
	public boolean isEntity(PersistentType jpt) {
		String mappingKey = getTypeMapping(jpt).getKey();		
		return (mappingKey == MappingKeys.ENTITY_TYPE_MAPPING_KEY);
	}	
	
	/**
	 * Checks whether the persistent type is of mapped superclass type.
	 * @param jpt
	 * @return true, if the given jpt is a mapped superclass, false, otherwise.
	 */
	public boolean isMappedSuperclass(PersistentType jpt) {
		String mappingKey = getTypeMapping(jpt).getKey();
		return (mappingKey == MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
	}
	
	/**
	 * Checks whether the persistent type is of an embeddable type.
	 * @param jpt
	 * @return true, if the given jpt is an embeddable class, false otherwise.
	 */
	public boolean isEmbeddable(PersistentType jpt){
		String mappingKey = getTypeMapping(jpt).getKey();
		return (mappingKey == MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
	}
	
	public String getSpecifiedEntityName(PersistentType jpt){
		TypeMapping jtm = getTypeMapping(jpt);
		if (jtm instanceof Entity) {
			Entity gje = (Entity) jtm;
			return gje.getSpecifiedName();
		}
		return jtm.getName();
	}
	
	public void renameEntity(PersistentType jpt, String newName) {
		TypeMapping jtm = getTypeMapping(jpt);
		if (jtm instanceof Entity) {
			Entity gje = (Entity) jtm;
			gje.setSpecifiedName(newName);
		}
	}
	
	public MappingFileRef getOrmXmlByForPersistentType(PersistentType type){
		for(MappingFileRef mappingFileRef : type.getPersistenceUnit().getMappingFileRefs()){
			if(mappingFileRef.getPersistentType(type.getName()) != null){
				return mappingFileRef;
			}
		}
		return null;
	}
	
	public PersistentType getOrmXmlPersistentTypeByName(JpaProject jpaProject, String fqnTypeName){
		for(MappingFileRef mappingFileRef : getPersistenceUnit(jpaProject).getMappingFileRefs()){
			PersistentType type = mappingFileRef.getPersistentType(fqnTypeName);
			if(type != null){
				return type;
			}
		}
		return null;
	}
	
	public PersistentAttribute getORMPersistentAttribute(PersistentAttribute jpa){
		PersistentType type = getOrmXmlPersistentTypeByName(jpa.getParent().getJpaProject(), jpa.getDeclaringPersistentType().getName());
		if(type == null)
			return null;
		for(PersistentAttribute attr : type.getAllAttributes()){
			if(attr.getName() != null && attr.getName().equals(jpa.getName()))
				return attr;
		}
		
		return null;
	}
	
	/**
	 * Find the first {@link HasReferenceRelation} for the given embeddable class from all existing
	 * {@link HasReferanceRelation} in the diagram. 
	 * @param embeddable - the given embeddable class
	 * @param fp
	 * @return the first {@link HasReferenceRelation} for the given embeddable class.
	 */
	public HasReferanceRelation findFisrtHasReferenceRelationByEmbeddable(PersistentType embeddable, IJPAEditorFeatureProvider fp){
		HashSet<HasReferanceRelation> hasReferencesConnections = fp.getAllExistingHasReferenceRelations();
		for(HasReferanceRelation ref : hasReferencesConnections){
			if (ref.getEmbeddable().getName().equals(embeddable.getName()) && isEntity(ref.getEmbeddingEntity())){
				return ref;
			}
		}
		return null;
	}
	
	/**
	 * Finds all java persistent types, which has an attribute, mapped as
	 * embedded id and type, the fully qualified name of the given embeddable.
	 * @param embeddable
	 * @param fp
	 * @return a collection of all java persistent types, that have an embedded id attribute of
	 * the given embeddable type.
	 */
	public HashSet<PersistentType> findAllJPTWithTheGivenEmbeddedId(PersistentType embeddable, IJPAEditorFeatureProvider fp){
		HashSet<PersistentType> embeddingEntities = new HashSet<PersistentType>();
		if(!isEmbeddable(embeddable))
			return embeddingEntities;
		ListIterator<PersistenceUnit> lit = embeddable.getJpaProject().getContextModelRoot().getPersistenceXml().getRoot().getPersistenceUnits().iterator();		
		PersistenceUnit pu = lit.next();
		for(PersistentType jpt : pu.getPersistentTypes()){
			if(!jpt.getName().equals(embeddable.getName())){
				for(PersistentAttribute jpa : (jpt).getAttributes()){
					if(isEmbeddedId(jpa) && JPAEditorUtil.getAttributeTypeNameWithGenerics(jpa).equals(embeddable.getName())){
						embeddingEntities.add(jpt);
					}
				}
			}
		}
		return embeddingEntities;
	}
	
	/**
	 * Find the first {@link HasReferenceRelation} for the given embeddable class from all existing
	 * {@link HasReferanceRelation} in the diagram. 
	 * @param embeddable - the given embeddable class
	 * @param fp
	 * @return the first {@link HasReferenceRelation} for the given embeddable class.
	 */
	public Set<HasReferanceRelation> findAllHasReferenceRelationsByEmbeddable(PersistentType embeddable, IJPAEditorFeatureProvider fp){
		HashSet<HasReferanceRelation> allHasRefForEmbeddable = new HashSet<HasReferanceRelation>();
		HashSet<HasReferanceRelation> hasReferencesConnections = fp.getAllExistingHasReferenceRelations();
		for(HasReferanceRelation ref : hasReferencesConnections){
			if (ref.getEmbeddable().getName().equals(embeddable.getName()) || ref.getEmbeddingEntity().getName().equals(embeddable.getName())){
				allHasRefForEmbeddable.add(ref);
			}
		}
		return allHasRefForEmbeddable;
	}
	
	public Set<HasReferanceRelation> findAllHasReferenceRelsByEmbeddableWithEntity(PersistentType embeddable, IJPAEditorFeatureProvider fp){
		Set<HasReferanceRelation> allRefs = findAllHasReferenceRelationsByEmbeddable(embeddable, fp);
		Set<HasReferanceRelation> entityRefs = new HashSet<HasReferanceRelation>();
		for(HasReferanceRelation ref : allRefs){
			if(isEntity(ref.getEmbeddingEntity())){
				entityRefs.add(ref);
			}
		}
		
		return entityRefs;
	}
	
	/**
	 * Create a relationship attribute.
	 * @param fp
	 * @param jpt - the referencing {@link PersistentType}
	 * @param attributeType - the referenced {@link PersistentType}
	 * @param mapKeyType
	 * @param attributeName - the name of the attribute
	 * @param actName - the actual name of the attribute
	 * @param isCollection - whether the attribute is of a collection type
	 * @param cu - the {@link ICompilationUnit} of the referencing {@link PersistentType}
	 * @return the newly created relationship attribute.
	 */
	public PersistentAttribute addAttribute(IJPAEditorFeatureProvider fp, PersistentType jpt, 
			PersistentType attributeType, String mapKeyType, String attributeName,
			String actName, boolean isCollection, ICompilationUnit cu) {
				
		try {
			if (doesAttributeExist(jpt, actName)) {
				return jpt.resolveAttribute(attributeName);
			}
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannnot create a new attribute with name " + attributeName, e); //$NON-NLS-1$				
		}
		PersistentAttribute res = makeNewAttribute(fp, jpt, cu, attributeName, attributeType.getName(), actName, mapKeyType, null, null, isCollection);		
		return res;
	}
	
	public boolean isCollection(AttributeMapping attributeMapping) {
		PersistentAttribute attribute = (PersistentAttribute) attributeMapping.getPersistentAttribute();
		String attributeType = attribute.getTypeName();
		
		if(attributeType == null) {
			PersistentType jpt = (PersistentType) attribute.getParent();
			jpt.getJpaProject().getContextModelRoot().synchronizeWithResourceModel();
			JavaResourceType jrt = jpt.getJavaResourceType();
			jrt.getJavaResourceCompilationUnit().synchronizeWithJavaSource();
			jpt.update();
			attribute = jpt.getAttributeNamed(attribute.getName());
			attributeType  = attribute.getTypeName();
		}

		if(attributeType != null && (attributeType.equals(JPAEditorConstants.COLLECTION_TYPE) || attributeType.equals(JPAEditorConstants.LIST_TYPE)
				|| attributeType.equals(JPAEditorConstants.SET_TYPE) || attributeType.equals(JPAEditorConstants.MAP_TYPE)
				|| attributeType.endsWith("[]"))) {  //$NON-NLS-1$
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a new basic entity attribute of type "java.lang.String"
	 * @param jpt - the entity, in which the new attribute will be added
	 * @param isCollection - whether the attribute is a collection
	 * @param fp
	 * @return the newly created attribute.
	 */
	public String createNewAttribute(PersistentType jpt, 
			boolean isCollection, IJPAEditorFeatureProvider fp) {

		String newAttrName = genUniqueAttrName(jpt, JPAEditorConstants.STRING_TYPE, fp);
		ICompilationUnit cu = fp.getCompilationUnit(jpt);
		makeNewAttribute(fp, jpt, cu, newAttrName, JPAEditorConstants.STRING_TYPE, newAttrName, JPAEditorConstants.STRING_TYPE, null, null, isCollection);
		return newAttrName;
	}
	public PersistentAttribute makeNewAttribute(IJPAEditorFeatureProvider fp, PersistentType jpt, ICompilationUnit cu, String attrName, String attrTypeName,
			String actName, String mapKeyType, String[] attrTypes, List<String> annotations, boolean isCollection) {

		if(cu == null){
			cu = fp.getCompilationUnit(jpt);
		}
		
		Command createNewAttributeCommand = new AddAttributeCommand(fp, jpt, attrTypeName, mapKeyType, attrName, actName, attrTypes, annotations, isCollection, cu);
		try {
			getJpaProjectManager().execute(createNewAttributeCommand, SynchronousUiCommandContext.instance());
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot create a new attribute with name " + attrName, e); //$NON-NLS-1$		
		}
		PersistentAttribute jpa = jpt.getAttributeNamed(attrName);
		if(jpa == null){
			jpa = jpt.getAttributeNamed(actName);
		}
		return jpa;
	}
	
	public void addOrmPersistentAttribute(PersistentType jpt, PersistentAttribute jpa, String mappingKey){
		MappingFileRef ormXml = getOrmXmlByForPersistentType(jpt);
		if(ormXml != null && ormXml.getMappingFile() != null) {
			OrmPersistentType ormPersistentType = (OrmPersistentType)ormXml.getMappingFile().getPersistentType(jpt.getName());
			if(ormPersistentType == null)
				return;
			if(mappingKey != null) {
				ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed(jpa.getName()), mappingKey);
			} else {
				if(ormPersistentType.getAttributeNamed(jpa.getName()) != null){
					ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed(jpa.getName()), jpa.getMappingKey());
				}
			}
		}
	}
	
	public void removeOrmPersistentAttribute(PersistentType jpt, String attributeName){
		MappingFileRef ormXml = JpaArtifactFactory.instance().getOrmXmlByForPersistentType(jpt);
		if(ormXml != null && ormXml.getMappingFile() != null) {
			OrmPersistentType ormPersistentType = (OrmPersistentType)ormXml.getMappingFile().getPersistentType(jpt.getName());
			OrmPersistentAttribute ormReadOnlyAttribute = ormPersistentType.getAttributeNamed(attributeName);
			if(ormReadOnlyAttribute instanceof OrmSpecifiedPersistentAttribute)
				ormPersistentType.removeAttributeFromXml((OrmSpecifiedPersistentAttribute) ormReadOnlyAttribute);
		}
	}
		
	/**
	 * Delete an attribute from the entity.
	 * @param jpt - the entity from which the attribute will be deleted
	 * @param attributeName - the name of the attribute to be deleted
	 * @param fp
	 */
	public void deleteAttribute(PersistentType jpt, String attributeName,
								IJPAEditorFeatureProvider fp) {
		
		synchronized (jpt) {
			Command deleteAttributeCommand = new DeleteAttributeCommand(null, jpt, attributeName, fp);
			try {
				getJpaProjectManager().execute(deleteAttributeCommand, SynchronousUiCommandContext.instance());
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Cannot delete attribute with name " + attributeName, e); //$NON-NLS-1$		
			}
		}
	}
	
	/**
	 * Generate unique attribute name.
	 * @param jpt - the entity in which the attribute will be created.
	 * @param attrTypeName - the basic attribute name
	 * @param fp
	 * @return an unique attribute name.
	 */
	public String genUniqueAttrName(PersistentType jpt, 
			String attrTypeName, IJPAEditorFeatureProvider fp) {
		
		ICompilationUnit ijl = fp.getCompilationUnit(jpt);
		IType type = null;
		type = ijl.findPrimaryType();
		Set<String> attrNames = new HashSet<String>();
		for (String name : jpt.getAttributeNames()) {
			attrNames.add(name);
		}		
		String name = null;
		for (int i = 1; i < 10000000; i++) {
			name = "attribute" + i;  	//$NON-NLS-1$
			String attrNameWithCapitalLetter = JPAEditorUtil
					.capitalizeFirstLetter(name);
			String getterName = "get" + attrNameWithCapitalLetter; //$NON-NLS-1$
			String setterName = "set" + attrNameWithCapitalLetter;  //$NON-NLS-1$
			if (!attrNames.contains(name)) {
				IField fld = type.getField(name);
				IMethod getter = type.getMethod(getterName, new String[] {});
				IMethod setter1 = type.getMethod(setterName,
						new String[] { attrTypeName });
				IMethod setter2 = type.getMethod(setterName,
						new String[] { "QSet<Q" + attrTypeName + ";>;" }); //$NON-NLS-1$ //$NON-NLS-2$
				IMethod setter3 = type
						.getMethod(
								setterName,
								new String[] { "QCollection<Q" + attrTypeName + ";>;" }); //$NON-NLS-1$ //$NON-NLS-2$
				IMethod setter4 = type.getMethod(setterName,
						new String[] { "QSet;" }); //$NON-NLS-1$
				IMethod setter5 = type.getMethod(setterName,
						new String[] { "QCollection;" }); //$NON-NLS-1$
				if (!fld.exists() && !getter.exists() && !setter1.exists()
						&& !setter2.exists() && !setter3.exists()
						&& !setter4.exists() && !setter5.exists())
					break;
			}
		}
		return name;
	}
	
	/**
	 * Gets the PersistentAttribute mapping. By priority, first, is trying to be gotten the orm xml defined mapping.
	 * If the PersistentAttribute is not added in any orm.xml, then the JavaPersistentAttribute mapping is returned.
	 * @param pa - the persistent attribute
	 * @return the persistent attribute mapping
	 */
	public AttributeMapping getAttributeMapping(PersistentAttribute pa) {
		AttributeMapping mapping = null;
		if (pa != null) {
			PersistentType jpt = (PersistentType) pa.getParent();
			MappingFileRef ref = getOrmXmlByForPersistentType(jpt);
			if (ref != null) {
				PersistentAttribute rpa = ref.getPersistentType(jpt.getName())
						.getAttributeNamed(pa.getName());
				if(rpa != null) {
					mapping = rpa.getMapping();
				} else {
					mapping = pa.getMapping();
				}
			} else {
				mapping = pa.getMapping();
			}
		}
		return mapping;
	}
	
	/**
	 * Gets the PersistentType mapping. By priority, first, is trying to be gotten the orm xml defined mapping.
	 * If the PersistentType is not added in any orm.xml, then the JavaPersistentType mapping is returned.
	 * @param jpt - the persistent type
	 * @return the persistent type mapping
	 */
	public TypeMapping getTypeMapping(PersistentType jpt) {
		TypeMapping typeMapping = null;
		if(jpt != null){
			MappingFileRef ref = getOrmXmlByForPersistentType(jpt);
			if (ref != null) {
				PersistentType pt = ref.getPersistentType(jpt.getName());
				if(pt != null) {
				typeMapping = pt.getMapping();
				} else {
					typeMapping = jpt.getMapping();
				}
			} else {
				typeMapping = jpt.getMapping();
			}
		}
		
		return typeMapping;
	}
	
	/**
	 * Get all annotations as string for the given attribute.
	 * @param persistentAttribite
	 * @return a list of strings of all current annotation names of the given attribute.
	 */
	public List<String> getAnnotationStrings(
			PersistentAttribute persistentAttribite) {
		
		PersistentType jpt = (PersistentType) persistentAttribite.getParent();
		JavaResourceType jrt = jpt.getJavaResourceType();
		CompilationUnit jdtCU = jrt.getJavaResourceCompilationUnit().buildASTRoot();
		JavaResourceAttribute jrpt = persistentAttribite.getJavaPersistentAttribute().getResourceAttribute();
		List<String> res = new LinkedList<String>();
		for (Annotation an : jrpt.getTopLevelAnnotations()) {
			org.eclipse.jdt.core.dom.Annotation jdtAn = an.getAstAnnotation(jdtCU);
			res.add(jdtAn.toString());
		}
		return res;
	}	
		
	/**
	 * Checks whether the attribute is an owner of the relationship.
	 * @param at
	 * @return true, if the attribute is owner of the relationship, false otherwise.
	 */
	private boolean isOwner(PersistentAttribute at) {
		AttributeMapping jam = getAttributeMapping(at);
		if (!(jam instanceof RelationshipMapping))
			return false;
		return ((RelationshipMapping)jam).isRelationshipOwner();
	}
	
	/**
	 * Collect all relationships for the given {@link PersistentType}.
	 * @param newJPT
	 * @param fp
	 * @return an collection of all relationships for the given {@link PersistentType}.
	 */
	private Collection<IRelation> produceAllIRelations(
			PersistentType newJPT, IJPAEditorFeatureProvider fp) {
		
		Collection<IRelation> res = produceIRelations(newJPT, null, fp);
		Iterator<IRelation> it = res.iterator();
		HashSet<PersistentType> checkedEntities = new HashSet<PersistentType>();
		while (it.hasNext()) {
			IRelation rel = it.next();
			checkedEntities.add(rel.getOwner());
			checkedEntities.add(rel.getInverse());
		}
		List<Shape> shapes = fp.getDiagramTypeProvider().getDiagram()
				.getChildren();
		Iterator<Shape> iter = shapes.iterator();
		while (iter.hasNext()) {
			Shape sh = iter.next();
			PersistentType jpt = (PersistentType) fp
					.getBusinessObjectForPictogramElement(sh);
			if (jpt == null)
				continue;
			//if (!checkedEntities.contains(jpt)) {
			Collection<IRelation> rels = produceIRelations(jpt, newJPT, fp);
			res.addAll(rels);
			//}
		}
		return res;
	 }
	
	/**
	 * Collect all "has-reference" relationships for the given {@link PersistentType}.
	 * @param newJPT
	 * @param fp
	 * @return an collection of all "has-reference" relationships for the given {@link PersistentType}.
	 */
	public Collection<HasReferanceRelation> produceAllEmbeddedRelations(PersistentType jpt, IJPAEditorFeatureProvider fp) {
		
		Collection<HasReferanceRelation> res = produceEmbRelations(jpt, null, fp);
		Iterator<HasReferanceRelation> it = res.iterator();
		HashSet<PersistentType> checkedEntities = new HashSet<PersistentType>();
		while (it.hasNext()) {
			HasReferanceRelation rel = it.next();
			checkedEntities.add(rel.getEmbeddable());
			checkedEntities.add(rel.getEmbeddingEntity());
		}
		List<Shape> shapes = fp.getDiagramTypeProvider().getDiagram().getChildren();
		Iterator<Shape> iter = shapes.iterator();
		while (iter.hasNext()) {
			Shape sh = iter.next();
			PersistentType embeddingEntity = (PersistentType) fp.getBusinessObjectForPictogramElement(sh);
			if (embeddingEntity == null)
				continue;
			Collection<HasReferanceRelation> rels = produceEmbRelations(embeddingEntity, jpt, fp);
			res.addAll(rels);
		}
		return res;
	 }
	
	/**
	 * Collect all "has-reference" relationships for the given {@link PersistentType}.
	 * @param embeddingEntity
	 * @param embeddable
	 * @param fp
	 * @return an collection of all "has-reference" relationships for the given {@link PersistentType}.
	 */
	private Collection<HasReferanceRelation> produceEmbRelations(PersistentType embeddingEntity,
			PersistentType embeddable, IJPAEditorFeatureProvider fp) {
		
		Collection<HasReferanceRelation> resSet = new HashSet<HasReferanceRelation>();
		HasReferanceRelation res = null;
		for (PersistentAttribute embeddingAttribute : embeddingEntity.getAttributes()) {
			try {
				AttributeMapping attributeMapping = getAttributeMapping(embeddingAttribute);
				if((attributeMapping instanceof EmbeddedMapping) || (attributeMapping instanceof ElementCollectionMapping2_0) || (attributeMapping instanceof EmbeddedIdMapping)){
					String attributeTypeName = getRelTypeName(embeddingAttribute);
					if(embeddable != null && !(attributeTypeName.equals(embeddable.getName()))) {
							continue;
					}
				PersistentType embeddableClass = findJPT(embeddingAttribute, fp, getRelTypeName(embeddingAttribute));
				if (embeddableClass != null) {
					res = produceEmbeddedRelation(embeddingAttribute, attributeMapping, embeddableClass, fp);
					if (res != null)
						resSet.add(res);
					}
				}				
			} catch (Exception e) { 
				throw new RuntimeException();
			}
			
		}
		return resSet;
	}
	
	/**
	 * Create a new relationship.
	 * @param persistentAttribite
	 * @param jpt2
	 * @param fp
	 * @return the newly created relationship.
	 */
	public IRelation produceIRelation(PersistentAttribute persistentAttribite, PersistentType jpt2,
			IJPAEditorFeatureProvider fp) {
		
		IRelation res = null;
		AttributeMapping attributeMapping = getAttributeMapping(persistentAttribite);
			if (attributeMapping instanceof RelationshipMapping) {
				if(jpt2 != null && !(getRelTypeName(persistentAttribite).equals(jpt2.getName()))){
						return res;
				}
				
				PersistentType relJPT = findJPT(persistentAttribite, fp, getRelTypeName(persistentAttribite));
				if (relJPT != null) {
						res = produceRelation(persistentAttribite, attributeMapping, relJPT, fp);
				}
		}
		return res;
				
	}

	/**
	 * Gets the parent {@link PersistentType} of an attribute
	 * @param persistentAttribite
	 * @param fp
	 * @param typeName
	 * @return the parent {@link PersistentType} of an attribute.
	 */
    public PersistentType findJPT(PersistentAttribute persistentAttribite, IJPAEditorFeatureProvider fp, String typeName) {
    	if(persistentAttribite == null)
    		return null;
		PersistentType relJPT = (PersistentType)fp.getBusinessObjectForKey(typeName);
		return relJPT;
    }
	    
	private Collection<IRelation> produceIRelations(
			PersistentType newJPT, PersistentType jpt2, IJPAEditorFeatureProvider fp) {
		
		Set<IRelation> res = new HashSet<IRelation>();
		for (PersistentAttribute at : newJPT.getAttributes()) {
			if(at.getJavaPersistentAttribute() == null)
				continue;
			IRelation rel = produceIRelation(at, jpt2, fp);
			if (rel != null)
				res.add(rel);
		}
		return res;
	}
	
	/**
	 * Returns the {@link PersistentType} registered in the {@link PersistenceUnit} with the given name.
	 * @param name
	 * @param pu
	 * @return the {@link PersistentType} registered in the {@link PersistenceUnit} with the given name.
	 */
	public PersistentType getJPT(String name, PersistenceUnit pu) {
		pu.getJpaProject().getContextModelRoot().synchronizeWithResourceModel();
		pu.synchronizeWithResourceModel();
		pu.update();
		PersistentType jpt = pu.getPersistentType(name);
		int cnt = 0;
		while ((jpt == null) && (cnt < MAX_NUM_OF_ITERATIONS)) {
			try {
				Thread.sleep(PAUSE_DURATION);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Sleep interrupted", e); //$NON-NLS-1$		
			}
			pu.synchronizeWithResourceModel();
			jpt = pu.getPersistentType(name);
			cnt++;
		}		
		return jpt;
	}
	
	/**
	 * Returns all the attributes belonging to another entities and
	 * involved in a relationship  with the given entity.
	 * @param jpt
	 * @return a set of all attributes of an entity involved in relationship with the given entity.
	 */
	public Set<PersistentAttribute> getRelatedAttributes(PersistentType jpt) {
		Set<PersistentAttribute> res = new HashSet<PersistentAttribute>();
		Iterator<JpaFile> it = jpt.getJpaProject().getJpaFiles().iterator();
		PersistenceUnit pu = JpaArtifactFactory.INSTANCE.getPersistenceUnit(jpt.getJpaProject());
		while (it.hasNext()) {
			JpaFile jpaFile = it.next();
			JptResourceModel rm = jpaFile.getResourceModel();
			if (rm == null)
				continue;
			if (!JavaResourceCompilationUnit.class.isInstance(rm))
				continue;
			JavaResourceCompilationUnit jrcu = (JavaResourceCompilationUnit)rm;
			//CSN #130859 2010
			JavaResourceAbstractType jrt = jrcu.getPrimaryType();
			if (jrt == null)
				continue;
			String name = jrt.getTypeBinding().getQualifiedName();
			PersistentType jpt1 = pu.getPersistentType(name);
			if (jpt1 == null)
				continue;

			Set<PersistentAttribute> relAts = getRelAttributes(jpt, jpt1);
			if (relAts != null)
				res.addAll(relAts);
		}
		return res;
	}

	/**
	 * Return the attribute (if any) belonging to jpt and involved in a relationship with relJPT.
	 * @param jpt
	 * @param relJPT
	 * @return the attribute belonging to jpt and involved in a relationship with relJPT.
	 */
	private Set<PersistentAttribute> getRelAttributes(PersistentType jpt,
			PersistentType relJPT) {

		Set<PersistentAttribute> res = new HashSet<PersistentAttribute>();
		for (PersistentAttribute at : relJPT.getAttributes()) {
			AttributeMapping attributeMapping = getAttributeMapping(at);
				if ((attributeMapping instanceof RelationshipMapping) || (attributeMapping instanceof EmbeddedMapping) || (attributeMapping instanceof EmbeddedIdMapping)
						|| (attributeMapping instanceof ElementCollectionMapping2_0)) {
					String relTypeName = getRelTypeName(at);
					if (relTypeName == null || !relTypeName.equals(jpt.getName()))
						continue;
					res.add(at);
				}			
		}
		return res;
	}
	
	public void renameEntityClass(PersistentType jpt, String newEntityName, IJPAEditorFeatureProvider fp) {
		
		Command renameEntityCommand = new RenameEntityCommand(jpt, newEntityName, fp);
		try {
			getJpaProjectManager().execute(renameEntityCommand, SynchronousUiCommandContext.instance());
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot rename entity " + jpt.getName(), e); //$NON-NLS-1$		
		}
	}

	public PersistentAttribute renameAttribute(PersistentType jpt,
			String oldName, String newName, String inverseEntityName,
									 IJPAEditorFeatureProvider fp) throws InterruptedException {
		newName = JPAEditorUtil.decapitalizeFirstLetter(newName);
		if (isMethodAnnotated(jpt)) {		
			newName = JPAEditorUtil.produceValidAttributeName(newName);
		} 
		newName = JPAEditorUtil.produceUniqueAttributeName(jpt, newName);
		PersistenceUnit pu = null;
		PersistentAttribute oldAt = null;
				
		if(jpt instanceof OrmPersistentType){
			oldAt = ((OrmPersistentType)jpt).getJavaPersistentType().getAttributeNamed(oldName);
		} else {
			oldAt = jpt.getAttributeNamed(oldName);
		}
		
		if(oldAt == null){
			return null;
		}
		
		if(oldAt.getParent() == null){
			oldAt.getDeclaringPersistentType();
		}
		
		fp.addAddIgnore((PersistentType)oldAt.getParent(), newName);
		fp.addRemoveIgnore((PersistentType)oldAt.getParent(), oldName);
		
		String attributeTypeName = getRelTypeName(oldAt);

		Command renameAttributeCommand = new RenameAttributeCommand(null, jpt, oldName, newName, fp);
		getJpaProjectManager().execute(renameAttributeCommand, SynchronousUiCommandContext.instance());
			
		PersistentAttribute newAt = jpt.getAttributeNamed(newName);
		if (newAt == null) {
			return null;
		}
		
		fp.addRemoveIgnore(jpt, oldName);
		try {
			fp.replaceAttribute(oldAt, newAt);
		} catch (Exception e) {
			return newAt;
		}
		
		updateIRelationshipAttributes(jpt, inverseEntityName, fp, pu, oldAt,
				newAt, attributeTypeName);
		
		return newAt;
	}

	private void updateIRelationshipAttributes(PersistentType jpt,
			String inverseEntityName, IJPAEditorFeatureProvider fp,
			PersistenceUnit pu, PersistentAttribute oldAt,
			PersistentAttribute newAt, String typeName) throws InterruptedException {
		Set<IRelation> rels = fp.getRelationRelatedToAttribute(oldAt, typeName);
		Iterator<IRelation> iter = rels.iterator();
		while(iter.hasNext()){
			IRelation rel = iter.next();
			updateRelationship(jpt, inverseEntityName, fp, pu, oldAt, newAt, rel);

		}
		
		HashSet<PersistentType> embeddingEntities = findAllJPTWithTheGivenEmbeddedId(jpt, fp);
		if(embeddingEntities != null && !embeddingEntities.isEmpty()){
			renameMapsIdAttributeValue(oldAt, newAt, embeddingEntities);
		}
		
	}

	private void updateRelationship(PersistentType jpt,
			String inverseEntityName, IJPAEditorFeatureProvider fp,
			PersistenceUnit pu, PersistentAttribute oldAt,
			PersistentAttribute newAt, IRelation rel)
			throws InterruptedException {
		String inverseAttributeName = null;
		PersistentType inverseJPT = null;
		if (IBidirectionalRelation.class.isInstance(rel)) {
			inverseJPT = rel.getInverse();
			if (inverseJPT != oldAt.getParent()) {
				pu = JpaArtifactFactory.INSTANCE.getPersistenceUnit(jpt);
				inverseAttributeName = rel.getInverseAttributeName();
			}
			
			if(!inverseJPT.getResource().exists()) {
				inverseJPT = (PersistentType) pu.getManagedType(inverseEntityName);
			}
		}
		
		if (inverseAttributeName != null) {
			Command changeMappedByValueCommand = new SetMappedByNewValueCommand(fp, pu, inverseJPT.getName(), inverseAttributeName, newAt.getName(), oldAt);
			getJpaProjectManager().execute(changeMappedByValueCommand, SynchronousUiCommandContext.instance());		
		}
		if (rel != null) {
			updateRelation(jpt, fp, rel);
			if(hasIDClass(jpt)) {
				String idClassFQN = getIdType(jpt);
				IJavaProject javaProject = JavaCore.create(jpt.getJpaProject().getProject());
				IType type = getType(javaProject, idClassFQN);
				if(type != null && type.getField(oldAt.getName()).exists()){
					Command renameAttributeCommand = new RenameAttributeCommand(type.getCompilationUnit(), null, oldAt.getName(), newAt.getName(), fp);
					getJpaProjectManager().execute(renameAttributeCommand, SynchronousUiCommandContext.instance());
				}
			}
		}
	}

	private void renameMapsIdAttributeValue(PersistentAttribute oldAt,
			PersistentAttribute newAt, HashSet<PersistentType> embeddingEntities) {

		for(PersistentType embeddingEntity : embeddingEntities){
			for(PersistentAttribute attr : embeddingEntity.getAttributes()){
				
				AttributeMapping attributeMapping = getAttributeMapping(attr);
				if(attributeMapping instanceof SingleRelationshipMapping2_0){
					DerivedIdentity2_0 identity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
					if(identity.usesMapsIdDerivedIdentityStrategy()){
						MapsIdDerivedIdentityStrategy2_0 strategy = identity.getMapsIdDerivedIdentityStrategy();
						String attributeName = strategy.getSpecifiedIdAttributeName();
						if(attributeName != null && attributeName.equals(oldAt.getName())){
							strategy.setSpecifiedIdAttributeName(newAt.getName());
							Annotation ann = attr.getJavaPersistentAttribute().getResourceAttribute().getAnnotation(MapsIdAnnotation2_0.ANNOTATION_NAME);
							if(ann != null){
								((MapsIdAnnotation2_0)ann).setValue(newAt.getName());
							}
						}
					}
				}
			}
		}
	}
	
	
	
	private JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}

	private void updateRelation(PersistentType jpt, IJPAEditorFeatureProvider fp, IRelation rel) {
		
		if(!rel.getInverse().getResource().exists() || !rel.getOwner().getResource().exists()){
			PictogramElement peRel = fp.getPictogramElementForBusinessObject(rel);
			if(peRel != null && peRel instanceof Connection) {
				Graphiti.getPeService().deletePictogramElement(peRel);
			}
			
		}
		
		UpdateAttributeFeature updateFeature = new UpdateAttributeFeature(fp);
		if (jpt.equals(rel.getInverse())) {
			updateFeature.reconnect(rel.getOwner());
		} else {
			updateFeature.reconnect(rel.getInverse());
		}
	}

	/**
	 * Create a new {@link IRelation}.
	 * @param persistentAttribite
	 * @param an
	 * @param relJPT
	 * @param fp
	 * @return the newly created {@link IRelation}
	 */

	private IRelation produceRelation(PersistentAttribute persistentAttribite, AttributeMapping an,
			PersistentType relJPT, IJPAEditorFeatureProvider fp) {

		Hashtable<PersistentAttribute, AttributeMapping> ht = getRelAttributeAnnotation(
				persistentAttribite, relJPT, fp);
		if (ht == null) {
			return produceUniDirRelation((PersistentType)persistentAttribite
					.getParent(), persistentAttribite, an, relJPT, fp);
		} else {
			PersistentAttribute relAt = ht.keys().nextElement();
			AttributeMapping relAn = ht.get(relAt);
			return produceBiDirRelation((PersistentType)persistentAttribite
					.getParent(), persistentAttribite, an, relJPT,
					relAt, relAn, fp);
		}
		
	}
	
	/**
	 * Create a new {@link HasReferanceRelation}.
	 * @param embeddingAttribute
	 * @param an
	 * @param embeddable
	 * @param fp
	 * @return the newly created {@link HasReferanceRelation}.
	 */
	private HasReferanceRelation produceEmbeddedRelation(PersistentAttribute embeddingAttribute, AttributeMapping an,
			PersistentType embeddable, IJPAEditorFeatureProvider fp) {
		
		if (!JPAEditorUtil.getCompilationUnit((PersistentType) embeddingAttribute.getParent()).exists())
			return null;
		PersistentType embeddingEntity = (PersistentType) embeddingAttribute.getParent();
		String embeddedAttributeName = embeddingAttribute.getName();
		HasReferanceRelation res = null;
		if ((an instanceof EmbeddedMapping) || (an instanceof EmbeddedIdMapping)) {
			if (!fp.doesEmbeddedRelationExist(embeddable, embeddingEntity, embeddedAttributeName, HasReferenceType.SINGLE)) {
				res = new HasSingleReferenceRelation(embeddingEntity, embeddable);
			}
		} else if (an instanceof ElementCollectionMapping2_0) {
			if (!fp.doesEmbeddedRelationExist(embeddable, embeddingEntity, embeddedAttributeName, HasReferenceType.COLLECTION)){
				res = new HasCollectionReferenceRelation(embeddingEntity, embeddable);
			}
		}
		if (res != null){
			res.setEmbeddedAnnotatedAttribute(embeddingAttribute);
		}
		return res;		
	}
	
	/**
	 * Determine whether the relationship is biderectional or unidirectional.
	 * If there is a mappedBy attribute, which value is the same as the given attribute,
	 * then the relationship is bidirectional, oderwise - unidirectional.
	 * @param jpa - the owner relationship attribute
	 * @param relJPT - inverse {@link PersistentType}.
	 * @param fp
	 * @return a {@link Hashtable} containing the pair: inverseAttribute <-> relation annotation.
	 */
	private Hashtable<PersistentAttribute, AttributeMapping> getRelAttributeAnnotation(
			PersistentAttribute jpa, PersistentType relJPT, IJPAEditorFeatureProvider fp) {

		PersistentType jpt = (PersistentType) jpa.getParent();
		for (PersistentAttribute relEntAt : relJPT.getAttributes())	{
			AttributeMapping attributeMapping = getAttributeMapping(relEntAt);
			if (attributeMapping instanceof RelationshipMapping) {
				MappingRelationship mappingRel = ((RelationshipMapping)attributeMapping).getRelationship();
				if(!(mappingRel instanceof MappedByRelationship)){
					continue;
				} else {
					String mappedByAttr = ((MappedByRelationship)mappingRel).getMappedByStrategy().getMappedByAttribute();
					if(mappedByAttr == null)
						continue;
					
					String[] mappedByStrings = mappedByAttr.split(JPAEditorConstants.MAPPED_BY_ATTRIBUTE_SPLIT_SEPARATOR);
					if(mappedByStrings.length>1){
						jpt = getInvolvedEntity(fp, jpt, relEntAt, mappedByStrings);	
					} else {
						String mappedBy = ((MappedByRelationship)mappingRel).getMappedByStrategy().getMappedByAttribute();
						if (!jpa.getName().equals(mappedBy)) 
							continue;
					}
				}
				String relTypeName = getRelTypeName(relEntAt);					
				if (relTypeName != null && jpt != null && !relTypeName.equals(jpt.getName())) 
					continue;
				Hashtable<PersistentAttribute, AttributeMapping> ht = new Hashtable<PersistentAttribute, AttributeMapping>();
				ht.put(relEntAt, attributeMapping);
				return ht;					
			}
		}
		return null;
	}

	/**
	 * If the value of the mappedBy attribute consist of two strings separated by a dot, that means that
	 * the bidirectional relationship is between an embeddable class and an entity.
	 * This method find the parent entity of the relation attribute. After that takes the embedded attribute of
	 * this entity and checks whether the parent of the embedded attributes is the same as the given embeddable class.
	 * If yes, then return the entity that contains the embedded attribute.
	 * @param fp
	 * @param jpt - the embedded class
	 * @param relEntAt - the relation attribute in the entity class
	 * @param mappedByStrings - all strings in the mappedBy attribute
	 * @return the entity involved in the bidirectional relationship between the embeddable class and some entity.
	 */
	private PersistentType getInvolvedEntity(IJPAEditorFeatureProvider fp, PersistentType jpt,
			PersistentAttribute relEntAt, String[] mappedByStrings) {
		String mappedBy = mappedByStrings[0];
		PersistentType involvedEntity = findJPT(relEntAt, fp, getRelTypeName(relEntAt));
		if(involvedEntity == null)
			return involvedEntity;
		PersistentAttribute embeddedAttribute = involvedEntity.getAttributeNamed(mappedBy);
		if(embeddedAttribute != null){
			PersistentType embeddedJPT = findJPT(embeddedAttribute, fp, getRelTypeName(embeddedAttribute));
			if(embeddedJPT != null && jpt != null && embeddedJPT.getName().equals(jpt.getName())) {
				jpt = involvedEntity;
			}
		}
		return jpt;
	}
	
	/**
	 * Create unidirectional relationship.
	 * @param jpt
	 * @param at
	 * @param an
	 * @param relJPT
	 * @param fp
	 * @return the newly created unidirectional relationship.
	 */
	private IUnidirectionalRelation produceUniDirRelation(
			PersistentType jpt, PersistentAttribute at, AttributeMapping an,
			PersistentType relJPT, IJPAEditorFeatureProvider fp) {
		
		if (!isOwner(at) || !JPAEditorUtil.getCompilationUnit((PersistentType) at.getParent()).exists())
			return null;
		IUnidirectionalRelation res = createUniDirRelationshipDependsOnTheType(jpt, at, relJPT, fp, an);
		if (res != null)
			res.setAnnotatedAttribute(at);
		return res;
	}

	private IUnidirectionalRelation createUniDirRelationshipDependsOnTheType(
			PersistentType jpt, PersistentAttribute at,
			PersistentType relJPT, IJPAEditorFeatureProvider fp,
			AttributeMapping attributeMapping) {
		IUnidirectionalRelation res = null;
		String attrName = at.getName();
		if (attributeMapping instanceof OneToOneMapping) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, null, RelType.ONE_TO_ONE,
					RelDir.UNI))
				res = new OneToOneUniDirRelation(fp, jpt, relJPT, attrName, false, false);
		} else if (attributeMapping instanceof OneToManyMapping) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, null, RelType.ONE_TO_MANY,
					RelDir.UNI))
				res = new OneToManyUniDirRelation(fp, jpt, relJPT, attrName, false);
		} else if (attributeMapping instanceof ManyToOneMapping) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, null, RelType.MANY_TO_ONE,
					RelDir.UNI))
				res = new ManyToOneUniDirRelation(fp, jpt, relJPT, attrName, false, false);
		} else if (attributeMapping instanceof ManyToManyMapping) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, null, RelType.MANY_TO_MANY,
					RelDir.UNI))
				res = new ManyToManyUniDirRelation(fp, jpt, relJPT, attrName, false);
		}
		return res;
	}
	
	/**
	 * Create bidirectional relationship.
	 * @param jpt
	 * @param at
	 * @param an
	 * @param relJPT
	 * @param relAt
	 * @param relAn
	 * @param fp
	 * @return the newly created bidirectional relationship.
	 */
	private IBidirectionalRelation produceBiDirRelation(PersistentType jpt,
			PersistentAttribute at, AttributeMapping an,
			PersistentType relJPT, PersistentAttribute relAt,
			AttributeMapping relAn, IJPAEditorFeatureProvider fp) {
		if (!attributeMappingsMatch(an, relAn)) 
			return null;
		if (an instanceof OneToManyMapping) 
			return produceBiDirRelation(relJPT, relAt, relAn, jpt, at, an, fp);
		if (!isOwner(at) && !isOwner(relAt)) 
			return null;
		if (an instanceof ManyToOneMapping) {
			if (!isOwner(at) || isOwner(relAt)) 
				return null;
		}
		if (an instanceof ManyToManyMapping) {
			if (isOwner(at) && isOwner(relAt)) 
				return null;
			if (!isOwner(at)) 
				return produceBiDirRelation(relJPT, relAt, relAn, jpt, at, an,
						fp);
		}		
		String ownerAttrName = at.getName();
		String inverseAttrName = relAt.getName();
		
		AttributeMapping m = getAttributeMapping(relAt);
		
		if ((m != null) && (m instanceof RelationshipMapping)) {
			RelationshipMapping relMapping = (RelationshipMapping) m;
			MappingRelationship mappingRel = relMapping.getRelationship();
			if(!(mappingRel instanceof MappedByRelationship)){
				return null;
			} else {
				String mappedBy = ((MappedByRelationship)mappingRel).getMappedByStrategy().getMappedByAttribute();
				if (mappedBy == null)
					return null;
			}
		}
		
		IBidirectionalRelation res = createBiDirRelationshipDependsOnTheType(
				jpt, relJPT, fp, an, ownerAttrName, inverseAttrName);
		if (res != null) {
			res.setOwnerAnnotatedAttribute(at);
			res.setInverseAnnotatedAttribute(relAt);
		}
		return res;
	}

	private IBidirectionalRelation createBiDirRelationshipDependsOnTheType(
			PersistentType jpt, PersistentType relJPT,
			IJPAEditorFeatureProvider fp, AttributeMapping attributeMapping,
			String ownerAttrName, String inverseAttrName) {
		IBidirectionalRelation res = null;
		if (attributeMapping instanceof OneToOneMapping) {
			if (!fp.doesRelationExist(jpt, relJPT, ownerAttrName, inverseAttrName, RelType.ONE_TO_ONE,
					RelDir.BI))
				res = new OneToOneBiDirRelation(fp, jpt, relJPT, ownerAttrName,
						inverseAttrName, false, relJPT, false);
		} else if (attributeMapping instanceof ManyToOneMapping) {
			if (!fp.doesRelationExist(jpt, relJPT, ownerAttrName, inverseAttrName, RelType.MANY_TO_ONE,
					RelDir.BI))
				res = new ManyToOneBiDirRelation(fp, jpt, relJPT, ownerAttrName,
						inverseAttrName, false, relJPT, false);
		} else if (attributeMapping instanceof ManyToManyMapping) {
			if (!fp.doesRelationExist(jpt, relJPT, ownerAttrName, inverseAttrName, RelType.MANY_TO_MANY,
					RelDir.BI))
				res = new ManyToManyBiDirRelation(fp, jpt, relJPT, ownerAttrName,
						inverseAttrName, false, relJPT);
		}
		return res;
	}
	
	private boolean attributeMappingsMatch(AttributeMapping an1Name, AttributeMapping an2Name) {
		if ((an1Name instanceof OneToOneMapping) && (an2Name instanceof OneToOneMapping)) {
			return true;
		} else if ((an1Name instanceof OneToManyMapping)
				&& (an2Name instanceof ManyToOneMapping)) {
			return true;
		} else if ((an1Name instanceof ManyToOneMapping)
				&& (an2Name instanceof OneToManyMapping)) {
			return true;
		} else if ((an1Name instanceof ManyToManyMapping)
				&& (an2Name instanceof ManyToManyMapping)) {
			return true;
		}
		return false;
	}
	
	private boolean doesAttributeExist(PersistentType jpt, String name)
											throws JavaModelException {
		boolean exists = false;
		if (jpt.resolveAttribute(name) != null) {
			return true;
		}
		return exists;
	}
	
	public PersistenceUnit getPersistenceUnit(JpaFile jpaFile) {
		JpaProject jpaProject  = jpaFile.getJpaProject();
		if (jpaProject == null)
			return null;
		return getPersistenceUnit(jpaProject); 
	}
	
	public PersistenceUnit getPersistenceUnit(JpaProject project) {
		if(project.getContextModelRoot().getPersistenceXml() == null)
			return null;
		return project.getContextModelRoot().getPersistenceXml().getRoot()
				.getPersistenceUnits().iterator().next();
	}
	
	public PersistenceUnit getPersistenceUnit(PersistentType jpt) {
		return jpt.getPersistenceUnit(); 
	}

	public boolean isMethodAnnotated(PersistentAttribute attr) {
		return attr.getAccess() == AccessType.PROPERTY;
	}

	@SuppressWarnings("unchecked")
	public boolean isMethodAnnotated(PersistentType jpt) {
		ListIterator<PersistentAttribute> li = (ListIterator<PersistentAttribute>) jpt.getAttributes().iterator();
		if (!li.hasNext())
			return false;
		return (isMethodAnnotated(li.next()));
	}
	
	public void remakeRelations(IJPAEditorFeatureProvider fp,
			ContainerShape cs, PersistentType jpt) {
		if (cs == null) 
			cs = (ContainerShape)fp.getPictogramElementForBusinessObject(jpt);
		if (cs == null)
			return;
		removeOldRelations(fp, cs);
		addNewRelations(fp, jpt);
	}
	
	public String getTableName(PersistentType jpt) {
		if (jpt == null)
			return null;
		
		TypeMapping typeMapping = getTypeMapping(jpt);
		String tableName = typeMapping.getPrimaryTableName();
		
		return tableName;
	}
	
	
	public void setTableName(PersistentType jpt, String tableName) {
		if (jpt == null)
			return;
		TypeMapping typeMapping = getTypeMapping(jpt);
		if(typeMapping instanceof Entity){
			((Entity)typeMapping).getTable().setSpecifiedName(tableName);
		}	
	}

	private void removeOldRelations(IJPAEditorFeatureProvider fp,
			ContainerShape cs) {
		Set<IRemoveContext> ctxs = new HashSet<IRemoveContext>();
		Iterator<Connection> iter = Graphiti.getPeService().getAllConnections(cs).iterator();
		while (iter.hasNext()) {
			Connection conn = iter.next();
			IRemoveContext ctx = new RemoveContext(conn);
			ctxs.add(ctx);
		}
		Iterator<IRemoveContext> itCtx = ctxs.iterator();
		while (itCtx.hasNext()) {
			IRemoveContext ctx = itCtx.next();
			RemoveRelationFeature ft = new RemoveRelationFeature(fp);
			ft.remove(ctx);
		}		
	}
	
	public void addNewRelations(IJPAEditorFeatureProvider fp,
			PersistentType jpt) {
		addIRelationships(fp, jpt);
		addEmbeddedRelation(fp, jpt);
		rearrangeIsARelations(fp);
	}

	private void addEmbeddedRelation(IJPAEditorFeatureProvider fp,
			PersistentType jpt) {
		Collection<HasReferanceRelation> newEmbeddedRels = produceAllEmbeddedRelations(jpt, fp);
		Iterator<HasReferanceRelation> relationIterator = newEmbeddedRels.iterator();
		while (relationIterator.hasNext()) {
			HasReferanceRelation rel = relationIterator.next();
			addNewEmbeddedRelation(fp, rel);
		}
	}

	private void addIRelationships(IJPAEditorFeatureProvider fp,
			PersistentType jpt) {
		Collection<IRelation> selfRels = new HashSet<IRelation>(); 
		Collection<IRelation> newRels = produceAllIRelations(jpt, fp);
		Iterator<IRelation> relsIt = newRels.iterator();
		while (relsIt.hasNext()) {
			IRelation rel = relsIt.next();
			if (rel.getOwner() == rel.getInverse()) {
				selfRels.add(rel);
				continue;
			}
			addNewRelation(fp, rel);
		}
		relsIt = selfRels.iterator();
		while (relsIt.hasNext()) {
			IRelation rel = relsIt.next();
			addNewRelation(fp, rel);
		}
	}
	
	public void addIsARelations(IJPAEditorFeatureProvider fp,
								   Collection<IsARelation> rels) {
		Iterator<IsARelation> it = rels.iterator();
		while (it.hasNext()) {
			IsARelation rel = it.next();
			addNewIsARelation(fp, rel);
		}
	}
	
	private void addNewRelation(IJPAEditorFeatureProvider fp, IRelation rel) {
		AddConnectionContext ctx = new AddConnectionContext(JPAEditorUtil
				.getAnchor(rel.getOwner(), fp), JPAEditorUtil.getAnchor(rel
				.getInverse(), fp));
		ctx.setNewObject(rel);
		ctx.setTargetContainer(fp.getDiagramTypeProvider().getDiagram());
		AddRelationFeature ft = new AddRelationFeature(fp);
		ft.add(ctx);		
	}
	
	private void addNewIsARelation(IJPAEditorFeatureProvider fp, IsARelation rel) {
		AddConnectionContext ctx = new AddConnectionContext(JPAEditorUtil
				.getAnchor(rel.getSubclass(), fp), JPAEditorUtil.getAnchor(rel.getSuperclass(), fp));
		ctx.setNewObject(rel);
		ctx.setTargetContainer(fp.getDiagramTypeProvider().getDiagram());
		AddInheritedEntityFeature ft = new AddInheritedEntityFeature(fp);
		ft.add(ctx);		
	}
	
	private void addNewEmbeddedRelation(IJPAEditorFeatureProvider fp, HasReferanceRelation rel) {
		AddConnectionContext ctx = new AddConnectionContext(JPAEditorUtil
				.getAnchor(rel.getEmbeddingEntity(), fp), JPAEditorUtil.getAnchor(rel
				.getEmbeddable(), fp));
		ctx.setNewObject(rel);
		ctx.setTargetContainer(fp.getDiagramTypeProvider().getDiagram());
		AddHasReferenceRelationFeature ft = new AddHasReferenceRelationFeature(fp);
		ft.add(ctx);		
	}
	
	public String getRelTypeName(PersistentAttribute persistentAttribute) {
		String relTypeName = null;
		if(persistentAttribute != null) {
			AttributeMapping an = getAttributeMapping(persistentAttribute);
			
			if(!isCollection(an)){
				relTypeName = persistentAttribute.getTypeName();
			}			
			if(persistentAttribute.getJavaPersistentAttribute() == null) {
				persistentAttribute.getTypeName();
				return relTypeName;
			}
			JavaResourceAttribute jra = persistentAttribute.getJavaPersistentAttribute().getResourceAttribute();
			try {
				boolean isMap = jra.getTypeBinding().getQualifiedName().equals(JPAEditorConstants.MAP_TYPE);
				relTypeName = jra.getTypeBinding().getTypeArgumentName(isMap ? 1 : 0);
			} catch (Exception e) {}
			if (relTypeName == null && an != null && (an instanceof RelationshipMapping)) 
				relTypeName = ((RelationshipMapping)an).getFullyQualifiedTargetEntity();												
			if (relTypeName == null)
				relTypeName = JPAEditorUtil.getAttributeTypeName(jra);
		}
		return relTypeName;
	}
		
	public JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}
	
	public boolean hasIDClass(PersistentType jpt) {
		TypeMapping mapping = getTypeMapping(jpt);
		if(mapping instanceof Entity){
			String idClass = ((Entity)mapping).getIdClassReference().getIdClassName();
			if(idClass != null)
				return true;
		}
		return false;
	}

	public String getIdType(PersistentType jpt) {
		String idClass = null;
		TypeMapping mapping = getTypeMapping(jpt);
		if(mapping instanceof Entity){
			idClass = ((Entity)mapping).getIdClassReference().getFullyQualifiedIdClassName();
		}
		
		if (idClass != null)
			return idClass;
		PersistentAttribute[] ids = getIds(jpt);
		if (ids.length == 0)
			return null;
		String type = getRelTypeName(ids[0]);
		String wrapper = JPAEditorUtil.getPrimitiveWrapper(type);
		return (wrapper != null) ? wrapper : type;
	}

	public PersistentAttribute[] getIds(PersistentType jpt) {
		ArrayList<PersistentAttribute> res = new ArrayList<PersistentAttribute>();
		for (PersistentAttribute at : jpt.getAttributes()) {
			if (isId(at)) {
				res.add(at);
			}
		}
		PersistentAttribute[] ret = new PersistentAttribute[res.size()];
		return res.toArray(ret);
	}
	
	// returns true even if the primary key is inherited
	public boolean hasOrInheritsPrimaryKey(PersistentType jpt) {
		Iterable<PersistentAttribute> attributes = jpt.getAllAttributes();
		Iterator<PersistentAttribute> it = attributes.iterator();
		while (it.hasNext()) {
			PersistentAttribute at = it.next();
			if (isId(at))
				return true;
		}
		return false;
	}

	public boolean hasPrimaryKey(PersistentType jpt) {
		for (PersistentAttribute at : jpt.getAttributes()) 
			if (isId(at)) return true;
		return false;
	}

	private boolean hasSimplePk(PersistentType jpt) {
		for(PersistentAttribute at : jpt.getAttributes()){
			if(isSimpleId(at) && !hasIDClass(jpt)){
				return true;
			}
		}
		return false;
	}

	private PersistentAttribute getSimplePkAttribute(PersistentType jpt){
		for(PersistentAttribute jpa : jpt.getAttributes()){
			if(isSimpleId(jpa)){
				return jpa;
			}
		}
		return null;
	}
	
	public boolean isId(PersistentAttribute jpa) {
		return isSimpleId(jpa) || isEmbeddedId(jpa);
	}

	public boolean isSimpleId(PersistentAttribute jpa) {
		AttributeMapping attributeMapping  = getAttributeMapping(jpa);
		return (attributeMapping.getKey() == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
	}

	private boolean hasEmbeddedPk(PersistentType jpt){
		for(PersistentAttribute at : jpt.getAttributes()){
			if(isEmbeddedId(at)){
				return true;
			}
		}
		return false;
	}

	private PersistentAttribute getEmbeddedIdAttribute(PersistentType jpt){
		for(PersistentAttribute jpa : jpt.getAttributes()){
			if(isEmbeddedId(jpa)){
				return jpa;
			}
		}
		return null;
	}

	public boolean isEmbeddedId(PersistentAttribute jpa) {
		AttributeMapping attributeMapping = getAttributeMapping(jpa);
		return (attributeMapping.getKey() == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
	}

	public boolean isEmbeddedAttribute(PersistentAttribute jpa) {
		AttributeMapping attributeMapping = getAttributeMapping(jpa);
		return (attributeMapping.getKey() == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
	}

	public String getColumnName(PersistentAttribute jpa) {
		String columnName= null;
		
		AttributeMapping attributeMapping = getAttributeMapping(jpa);
		
		if(attributeMapping instanceof ColumnMapping){
			columnName = ((ColumnMapping)attributeMapping).getColumn().getName();
		}	
		return columnName; 
	}
	
	public IColorConstant getForeground(JPAEditorConstants.DIAGRAM_OBJECT_TYPE dot) {
		IColorConstant foreground = IColorConstant.WHITE;
		if(dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.MappedSupeclass)){
			foreground = JPAEditorConstants.MAPPED_SUPERCLASS_BORDER_COLOR;
		} else if(dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Entity)){
			foreground = JPAEditorConstants.ENTITY_BORDER_COLOR;
		} else if (dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Embeddable)){
			foreground = JPAEditorConstants.EMBEDDABLE_BORDER_COLOR;
		}
		return foreground;
	}

	public IColorConstant getBackground(JPAEditorConstants.DIAGRAM_OBJECT_TYPE dot) {
		IColorConstant background = IColorConstant.WHITE;
		if(dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.MappedSupeclass)){
			background = JPAEditorConstants.MAPPED_SUPERCLASS_BACKGROUND;
		} else if(dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Entity)){
			background = JPAEditorConstants.ENTITY_BACKGROUND;
		} else if (dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Embeddable)){
			background = JPAEditorConstants.EMBEDDABLE_BACKGROUND;
		}
		return background;
	}
	
	public String getRenderingStyle(JPAEditorConstants.DIAGRAM_OBJECT_TYPE dot) {
		String renderingStyle = ""; //$NON-NLS-1$
		if(dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.MappedSupeclass)){
			renderingStyle = IJPAEditorPredefinedRenderingStyle.GREEN_WHITE_GLOSS_ID;
		} else if(dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Entity)){
			renderingStyle = IJPAEditorPredefinedRenderingStyle.BLUE_WHITE_GLOSS_ID;
		} else if (dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Embeddable)){
			renderingStyle = IJPAEditorPredefinedRenderingStyle.VIOLET_WHITE_GLOSS_ID;
		}
		return renderingStyle;
	}
	
	public JPAEditorConstants.DIAGRAM_OBJECT_TYPE determineDiagramObjectType(PersistentType jpt) {
		if (this.isEntity(jpt)) {
			return JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Entity;
		} else if (this.isMappedSuperclass(jpt)) {
			return JPAEditorConstants.DIAGRAM_OBJECT_TYPE.MappedSupeclass;
		} else if (this.isEmbeddable(jpt)){
			return JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Embeddable;
		}
		throw new IllegalArgumentException();
	}
	
	public String generateIdName(PersistentType jpt) {
		String name = "id";		//$NON-NLS-1$
		String genName = name;
		for (int i = 0; i < 10000000; i++) {
			if (!hasAttributeNamed(jpt, genName))
				return genName;
			genName = name + "_" + i;	//$NON-NLS-1$
		}
		return genName;
	}
	
	
	private  boolean hasAttributeNamed(PersistentType jpt, String name) {
		Iterable<String> hier = jpt.getAllAttributeNames();
		Iterator<String> it = hier.iterator();
		while (it.hasNext()) {
			String atName = it.next();
			if (name.equals(atName))
				return true;
		}
		return false;
	}
	
	public String getMappedSuperclassPackageDeclaration(PersistentType jpt) throws JavaModelException {
		String packageName = null;
		IPackageDeclaration[] packages = JPAEditorUtil.getCompilationUnit(jpt)
				.getPackageDeclarations();
		if (packages.length > 0) {
			IPackageDeclaration packageDecl = packages[0];
			packageName = packageDecl.getElementName();
		}
		return packageName;
	}

	public void buildHierarchy(PersistentType superclass, PersistentType subclass, boolean build) {
	
		Command createNewAttributeCommand = new CreateEntityTypeHierarchy(superclass, subclass, build);
		try {
			getJpaProjectManager().execute(createNewAttributeCommand, SynchronousUiCommandContext.instance());
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot create hierarchy of entity type " + subclass.getName(), e); //$NON-NLS-1$		
		}
	}
	
	/**
	 * Adds derived identifier
	 * @param ownerJPT - the dependent entity (the relationship's owner entity)
	 * @param inverseJPT - the parent entity (the relationship's inverse/target entity)
	 * @param ownerAttr - the relationship's owner attribute
	 */
	public void calculateDerivedIdAttribute(PersistentType ownerJPT, PersistentType inverseJPT, PersistentAttribute ownerAttr) {
		String attributeType = null;
		if(hasSimplePk(inverseJPT)){

			PersistentAttribute jpa = getSimplePkAttribute(inverseJPT);
			attributeType  = JPAEditorUtil.getAttributeTypeNameWithGenerics(jpa);
		} else {
			if(hasIDClass(inverseJPT)){
				attributeType = getIdType(inverseJPT);
			} else if (hasEmbeddedPk(inverseJPT)){
				attributeType = JPAEditorUtil.getAttributeTypeNameWithGenerics(getEmbeddedIdAttribute(inverseJPT));
			}
		}
		addAppropriateDerivedIdAnnotation(ownerJPT, inverseJPT, ownerAttr, attributeType);

	}

	/**
	 * Adds the derived identifier annotation depending on the dependent entity's primary key type.
	 * @param ownerJPT - the dependent entity (the relationship's owner entity)
	 * @param inverseJPT - the parent entity (the relationship's inverse/target entity)
	 * @param ownerAttr - the relationship's owner attribute
	 * @param inverseIdClassFQN - he type of the primary key of the parent entity
	 */
	private void addAppropriateDerivedIdAnnotation(PersistentType ownerJPT,
			PersistentType inverseJPT, PersistentAttribute ownerAttr,
			String inverseIdClassFQN) {
		String annotationName = null;
		String mapsIdValue = null;
		boolean isXmlDefined = getORMPersistentAttribute(ownerAttr) != null;
		if(hasIDClass(ownerJPT)){
			annotationName = IdAnnotation.ANNOTATION_NAME;
			String ownerIdClassFQN = getIdType(ownerJPT);
			addDerivedIdAnnotation(ownerJPT, inverseJPT, ownerAttr, ownerIdClassFQN,
					inverseIdClassFQN, annotationName, isXmlDefined);
		} else if(hasEmbeddedPk(ownerJPT)){
			annotationName = MapsIdAnnotation2_0.ANNOTATION_NAME;
			String ownerIdClassFQN = JPAEditorUtil.getAttributeTypeNameWithGenerics(getEmbeddedIdAttribute(ownerJPT));
			mapsIdValue = addDerivedIdAnnotation(ownerJPT, inverseJPT, ownerAttr, ownerIdClassFQN,
					inverseIdClassFQN, annotationName, isXmlDefined);
		} else if(hasSimplePk(ownerJPT)){
			annotationName = MapsIdAnnotation2_0.ANNOTATION_NAME;
			if(!isXmlDefined) { 		
				ownerAttr.getJavaPersistentAttribute().getResourceAttribute().addAnnotation(annotationName);
			}
			mapsIdValue = getSimplePkAttribute(inverseJPT).getName();
		} else {
			annotationName = IdAnnotation.ANNOTATION_NAME;
			if(!isXmlDefined) { 		
				ownerAttr.getJavaPersistentAttribute().getResourceAttribute().addAnnotation(annotationName);
			}
		}
		addDerivedIdMapping(ownerAttr, mapsIdValue, annotationName);

		addJoinColumnIfNecessary(ownerAttr, inverseJPT);
	}

	/**
	 * Adds the appropriate derived identifier's annotation to the relationship's owner attribute.
	 * @param ownerJPT - the dependent entity (the relationship's owner entity)
	 * @param inverseJPT - the parent entity ( the relationship's inverse/target entity)
	 * @param ownerAttr - the relationship's owner attribute
	 * @param ownerIdClassFQN - the fully qualified name of the composite primary key's class 
	 * @param inverseIdClassFQN - the type of the primary key of the parent entity
	 * @param annotationName - the derived identifier's annotation (either @Id or @MapsId)
	 * @param isXmlDefined - boolean value, indicating whether the attribute is registered in the
	 * orm.xml, or not.
	 */
	private String addDerivedIdAnnotation(PersistentType ownerJPT,
			PersistentType inverseJPT, PersistentAttribute ownerAttr,
			String ownerIdClassFQN,	String inverseIdClassFQN, String annotationName, boolean isXmlDefined) {
		if(!inverseIdClassFQN.equals(ownerIdClassFQN)){
			String attributeType = JPAEditorUtil.returnSimpleName(inverseIdClassFQN);
			addFieldInCompositeKeyClass(inverseJPT, ownerAttr, ownerIdClassFQN, attributeType);
			if(!isXmlDefined) {
				Annotation ann = ownerAttr.getJavaPersistentAttribute().getResourceAttribute().addAnnotation(annotationName);
				if(ann != null && ann instanceof MapsIdAnnotation2_0){
					((MapsIdAnnotation2_0)ann).setValue(ownerAttr.getName());
				}
			}
			return ownerAttr.getName();
		} else {
			if(!isXmlDefined) {
				ownerAttr.getJavaPersistentAttribute().getResourceAttribute().addAnnotation(annotationName);
			}
		}
		
		return null;
	}
	
	private void addDerivedIdMapping(PersistentAttribute attr, String idAttributeName, String annotationName){
		AttributeMapping attributeMapping = getAttributeMapping(attr);
		if(attributeMapping instanceof SingleRelationshipMapping2_0) {
			DerivedIdentity2_0 identity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
		    if(annotationName.equals(IdAnnotation.ANNOTATION_NAME)) {
		    	identity.getIdDerivedIdentityStrategy().setValue(true);
		    } else {
		    	identity.getMapsIdDerivedIdentityStrategy().setSpecifiedIdAttributeName(idAttributeName);
		    }
		}
	}

	/**
	 * This method is called during the calculation of the derived identifier. If the dependent entity has a
	 * composite primary key, this method is used to be created a new field in the composite primary key's class.
	 * The field will be of the same type as type the primary key of the parent entity and the name will be the
	 * same as the name of the owner relationship's attribute. 
	 * @param inverseJPT - the parent entity (the relationship's inverse/target entity)
	 * @param ownerAttr - the dependent entity (the relationship's owner entity) 
	 * @param fqnClass - the fully qualified name of the composite primary key's class
	 * @param attributeTypeName - the attribute's type
	 */
	private void addFieldInCompositeKeyClass(PersistentType inverseJPT,
			PersistentAttribute ownerAttr, String fqnClass, String attributeTypeName) {
		IJavaProject javaProject = JavaCore.create(ownerAttr.getJpaProject().getProject());
		IType type = getType(javaProject, fqnClass);
		if(type != null && !type.getField(ownerAttr.getName()).exists()){
			ICompilationUnit unit = type.getCompilationUnit();
			PersistentType jpt = JPAEditorUtil.getJPType(unit);
			Command createNewAttributeCommand = new AddAttributeCommand(null, jpt, attributeTypeName, null, ownerAttr.getName(),
					ownerAttr.getName(), null, null, false, unit);
			try {
				getJpaProjectManager().execute(createNewAttributeCommand, SynchronousUiCommandContext.instance());
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Cannot create a new attribute with name " + ownerAttr.getName(), e); //$NON-NLS-1$		
			}
			if(jpt != null) {
				PersistentAttribute attr = jpt.getAttributeNamed(ownerAttr.getName());
				attr.getJavaPersistentAttribute().setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
			}
		}
	}
	
	public void addPersistentTypeToORMXml(JpaProject jpaProject, String entityName, String mapping){
		Command renameAttributeCommand = new AddPersistentTypeToOrmXmlCommand(jpaProject, mapping, entityName);
		try {
			getJpaProjectManager().execute(renameAttributeCommand, SynchronousUiCommandContext.instance());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void deletePersistentTypeFromORMXml(JpaProject jpaProject, PersistentType jpt){
		PersistenceUnit unit = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		if(unit.getMappingFileRefsSize() == 0)
			return;
		MappingFileRef fileRef = unit.getMappingFileRefs().iterator().next();
		if(fileRef == null)
			return;
	    MappingFile ref = fileRef.getMappingFile();
	    OrmXml ormXml = (OrmXml)ref;
	    OrmManagedType type = ormXml.getRoot().getManagedType(jpt.getName());
	    if(type != null){
	    	ormXml.getRoot().removeManagedType(type);
	    }
	}
	public IType getType(IJavaProject javaProject, String fqnClass) {
		try {
			IType type = javaProject.findType(fqnClass);
			return type;
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	} 
}
