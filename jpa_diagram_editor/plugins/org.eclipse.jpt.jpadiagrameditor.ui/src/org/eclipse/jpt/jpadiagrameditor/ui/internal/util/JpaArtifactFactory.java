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
import java.util.Locale;
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandExecutor;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableAnnotation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.AddAttributeCommand;
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
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
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
	private static String MAPPED_BY_ATTRIBUTE_SPLIT_SEPARATOR = "\\."; //$NON-NLS-1$
	
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
		Collection<JavaPersistentType> persistentTypes = fp.getPersistentTypes();
		Collection<IsARelation> res = new HashSet<IsARelation>();
		Iterator<JavaPersistentType> it = persistentTypes.iterator(); 
		HashSet<IsARelation> allExistingIsARelations = fp.getAllExistingIsARelations();
		while (it.hasNext()) {
			JavaPersistentType jpt = it.next();
			JavaPersistentType superclass = fp.getFirstSuperclassBelongingToTheDiagram(jpt);
			if (superclass == null)
				continue;
			IsARelation newRel = new IsARelation(jpt, superclass);
			if (!allExistingIsARelations.contains(newRel))
				res.add(newRel);
		}
		return res;
	}
		
	public void addOneToOneUnidirectionalRelation(IFeatureProvider fp, JavaPersistentType jpt, 
												  JavaPersistentAttribute attribute) {
		addOneToOneRelation(fp, jpt, attribute, null, null,
				JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL);
	}
	
	public void addOneToOneBidirectionalRelation (IFeatureProvider fp, JavaPersistentType jpt1, 
			JavaPersistentAttribute attribute1, JavaPersistentType jpt2,
												  JavaPersistentAttribute attribute2) {
		
		addOneToOneRelation(fp, jpt1, attribute1, jpt2, attribute2,
				JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL);
	}
	
	public void addOneToManyUnidirectionalRelation(IFeatureProvider fp, JavaPersistentType jpt, 
												   JavaPersistentAttribute attribute, boolean isMap) {
		
		addOneToManyRelation(fp, jpt, attribute, null, null,
				JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL, isMap);
	}
	
	public void addOneToManyBidirectionalRelation(IFeatureProvider fp, JavaPersistentType jpt1, 
			JavaPersistentAttribute attribute1, JavaPersistentType jpt2,
												  JavaPersistentAttribute attribute2, boolean isMap) {
		
		addOneToManyRelation(fp, jpt1, attribute1, jpt2, attribute2,
				JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL, isMap);
	}
	
	public void addManyToOneBidirectionalRelation(IFeatureProvider fp, JavaPersistentType jpt1, 
			JavaPersistentAttribute attribute1, JavaPersistentType jpt2,
												  JavaPersistentAttribute attribute2, boolean isMap) {
		
		addManyToOneRelation(fp, jpt1, attribute1, jpt2, attribute2,
				JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL, isMap);
	}
	
	public void addOneToOneRelation(IFeatureProvider fp,
			JavaPersistentType ownerJPT, JavaPersistentAttribute ownerAttibute,
			JavaPersistentType referencedJPT,
			JavaPersistentAttribute referencedAttribute, int direction) {
		
		setMappingKeyToAttribute(fp, ownerJPT, ownerAttibute, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {
			JavaPersistentAttribute resolvedAttribute = setMappingKeyToAttribute(fp, referencedJPT, referencedAttribute, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);			
			String mappedByAttr = getMappeByAttribute(fp, ownerJPT, ownerAttibute);
			setMappedByAnnotationAttribute(resolvedAttribute, referencedJPT, mappedByAttr);
		}

	}
	
	public void addManyToOneRelation(IFeatureProvider fp, JavaPersistentType manySideJPT,
			JavaPersistentAttribute manySideAttribute, JavaPersistentType singleSideJPT,
			JavaPersistentAttribute singleSideAttibute, int direction, boolean isMap) {
		
		setMappingKeyToAttribute(fp, manySideJPT, manySideAttribute, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);

		if (direction == JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL)
			return;
		
		JavaPersistentAttribute resolvedSingleSideAttribute = setMappingKeyToAttribute(fp, singleSideJPT, singleSideAttibute, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		String mappedByAttr = getMappeByAttribute(fp, manySideJPT, manySideAttribute);
		setMappedByAnnotationAttribute(resolvedSingleSideAttribute, singleSideJPT, mappedByAttr);
		if (isMap) {
			singleSideAttibute.getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		}
		
	}
	
	public void addOneToManyRelation(IFeatureProvider fp, JavaPersistentType singleSideJPT, 
									 JavaPersistentAttribute singleSideAttibute, 
									 JavaPersistentType manySideJPT, 
			JavaPersistentAttribute manySideAttribute, int direction, boolean isMap) {
				
		JavaPersistentAttribute resolvedSingleSideAttribute = setMappingKeyToAttribute(fp, singleSideJPT, singleSideAttibute, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {		
			setMappingKeyToAttribute(fp, manySideJPT, manySideAttribute, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			String mappedByAttr = getMappeByAttribute(fp, manySideJPT, manySideAttribute);
			setMappedByAnnotationAttribute(resolvedSingleSideAttribute, singleSideJPT, mappedByAttr);			
		} else {
			addJoinColumnIfNecessary(resolvedSingleSideAttribute, singleSideJPT);
		}		
		if (isMap)
			singleSideAttibute.getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	private String getMappeByAttribute(IFeatureProvider fp,
			JavaPersistentType ownerSideJPT, JavaPersistentAttribute ownerSideAttribute) {
		
		String mappedByAttr = ownerSideAttribute.getName();
		
		if(hasEmbeddableAnnotation(ownerSideJPT)){
			HasReferanceRelation ref = JpaArtifactFactory.INSTANCE.findFisrtHasReferenceRelationByEmbeddable(ownerSideJPT, (IJPAEditorFeatureProvider)fp);
			if(ref != null){
				JavaPersistentAttribute embeddingAttribute = ref.getEmbeddedAnnotatedAttribute();
				mappedByAttr  = embeddingAttribute.getName() + "." + ownerSideAttribute.getName(); //$NON-NLS-1$
			}
		}
		return mappedByAttr;
	}
	
	private void setMappedByAnnotationAttribute(JavaPersistentAttribute resolvedAttr, JavaPersistentType type1, String jpaName){

		JavaAttributeMapping mapping = resolvedAttr.getMapping();
		if (!(mapping instanceof RelationshipMapping)) {
			resolvedAttr.getResourceAttribute().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		}
		Annotation annotation = mapping.getMappingAnnotation();
		if (annotation == null) {
			resolvedAttr.getResourceAttribute().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
			annotation = mapping.getMappingAnnotation();
		}
		if (!(annotation instanceof OwnableRelationshipMappingAnnotation))
			return;
		((OwnableRelationshipMappingAnnotation)annotation).setMappedBy(jpaName);
	}

	private JavaPersistentAttribute setMappingKeyToAttribute(IFeatureProvider fp, JavaPersistentType jpt, JavaPersistentAttribute jpa, String mappingKey){
		JavaPersistentAttribute resolvedManySideAttribute = (JavaPersistentAttribute) jpt.resolveAttribute(jpa.getName());
		resolvedManySideAttribute.setMappingKey(mappingKey);
		return resolvedManySideAttribute;
	}
	
	private void addJoinColumnIfNecessary(JavaPersistentAttribute jpa,
			JavaPersistentType jpt) {

		if (JPAEditorUtil.checkJPAFacetVersion(jpa.getJpaProject(), JPAEditorUtil.JPA_PROJECT_FACET_10) ||
				JPADiagramPropertyPage.shouldOneToManyUnidirBeOldStyle(jpa
						.getJpaProject().getProject()))
			return;
		JavaPersistentAttribute[] ids = getIds(jpt);
		if (ids.length == 0)
			return;
		final String tableName = getTableName(jpt);
		if (ids.length == 1) {
			if (isSimpleId(ids[0])) {
				JoinColumnAnnotation an = (JoinColumnAnnotation) jpa
						.getResourceAttribute().addAnnotation(0, 
								JoinColumnAnnotation.ANNOTATION_NAME);
				String idColName = getColumnName(ids[0]);
				an.setName(tableName + "_" + idColName); //$NON-NLS-1$
				an.setReferencedColumnName(idColName);
			} else {
				Hashtable<String, String> atNameToColName = getOverriddenColNames(ids[0]);
				PersistenceUnit pu = getPersistenceUnit(jpt);
				String embeddableTypeName = ids[0].getTypeName();
				Embeddable emb = pu.getEmbeddable(embeddableTypeName);
				for (AttributeMapping am : emb.getAllAttributeMappings()) {
					JoinColumnAnnotation jc = (JoinColumnAnnotation) jpa.getResourceAttribute().addAnnotation(jpa.getResourceAttribute().getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME), JoinColumnAnnotation.ANNOTATION_NAME);
					JavaPersistentAttribute at = (JavaPersistentAttribute) am
							.getPersistentAttribute();
					String idColName = atNameToColName.get(at.getName());
					idColName = (idColName != null) ? idColName
							: getColumnName(at);
					jc.setName(tableName + "_" + idColName); //$NON-NLS-1$
					jc.setReferencedColumnName(idColName);
				}
			}
		} else {
			for (JavaPersistentAttribute idAt : ids) {
				JoinColumnAnnotation jc = (JoinColumnAnnotation) jpa.getResourceAttribute().addAnnotation( jpa.getResourceAttribute().getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME), JoinColumnAnnotation.ANNOTATION_NAME);
				String idColName = getColumnName(idAt);
				jc.setName(tableName + "_" + idColName); //$NON-NLS-1$
				jc.setReferencedColumnName(idColName);
			}
		}
	}

	private Hashtable<String, String> getOverriddenColNames(
			JavaPersistentAttribute embIdAt) {
		Hashtable<String, String> res = new Hashtable<String, String>();
		if(embIdAt.getResourceAttribute().getAnnotationsSize(AttributeOverrideAnnotation.ANNOTATION_NAME) > 0){
		AttributeOverrideAnnotation aon = (AttributeOverrideAnnotation) embIdAt
				.getResourceAttribute().getAnnotation(0, 
						AttributeOverrideAnnotation.ANNOTATION_NAME);
		if (aon != null) {
			ColumnAnnotation colAn = aon.getColumn();
			if (colAn == null)
				return res;
			String colName = colAn.getName();
			if (colName == null)
				return res;
			res.put(aon.getName(), colName);
			return res;
		}
		}
		Iterable<AttributeOverrideAnnotation> it = new SubListIterableWrapper<NestableAnnotation, AttributeOverrideAnnotation>(
			embIdAt.getResourceAttribute().getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME));
		for (AttributeOverrideAnnotation an : it) {
			ColumnAnnotation colAn = an.getColumn();
			if (colAn == null)
				continue;
			String colName = colAn.getName();
			if (colName == null)
				continue;
			res.put(an.getName(), colName);
		}
		return res;
	}	
	
	
	public void addManyToOneUnidirectionalRelation(IFeatureProvider fp, JavaPersistentType jpt, 
												   JavaPersistentAttribute attribute) {
		
		addManyToOneRelation(fp, jpt, attribute, null, null,
				JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL, false);
	}

	
	public void addManyToManyBidirectionalRelation(IFeatureProvider fp, JavaPersistentType jpt1, 
			JavaPersistentAttribute attribute1, JavaPersistentType jpt2,
												   JavaPersistentAttribute attribute2, boolean isMap) {
		
		addManyToManyRelation(fp, jpt1, attribute1, jpt2, attribute2,
				JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL, isMap);
	}
	
	public void addManyToManyUnidirectionalRelation(IFeatureProvider fp, JavaPersistentType annotatedJPT,
													JavaPersistentAttribute annotatedAttribute, boolean isMap) {
		
		addManyToManyRelation(fp, annotatedJPT, annotatedAttribute, null, null,
				JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL, isMap);
	}
	
	public void addManyToManyRelation(IFeatureProvider fp, JavaPersistentType ownerSideJPT, 
									  JavaPersistentAttribute ownerSideAttribute, 
									  JavaPersistentType inverseSideJPT, 
			JavaPersistentAttribute inverseSideAttibute, int direction, boolean isMap) {		
		
		JavaPersistentAttribute resolvedOwnerSideAttribute = setMappingKeyToAttribute(fp, ownerSideJPT, ownerSideAttribute, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		if (isMap)
			resolvedOwnerSideAttribute.getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {
			JavaPersistentAttribute resolvedInverseSideAttribute = setMappingKeyToAttribute(fp, inverseSideJPT, inverseSideAttibute, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			String mappedByAttr = getMappeByAttribute(fp, ownerSideJPT, ownerSideAttribute);
			setMappedByAnnotationAttribute(resolvedInverseSideAttribute, inverseSideJPT, mappedByAttr);
			
			if (isMap)
				resolvedInverseSideAttribute.getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		}		
		
	}
		
	public void restoreEntityClass(JavaPersistentType jpt, IJPAEditorFeatureProvider fp) {
		fp.restoreEntity(jpt);
	}    	
	
	public void forceSaveEntityClass(final JavaPersistentType jpt,
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
	
	public boolean deleteEntityClass(JavaPersistentType jpt, 
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
		JavaPersistentType jpt = getContextPersistentType(proj, typeName);
		while ((jpt != null) && (cnt < MAX_NUM_OF_ITERATIONS)) {
			try {
				Thread.sleep(PAUSE_DURATION);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Sleep interrupted", e); //$NON-NLS-1$				
			}
			cnt++;
		}
	}

	public JavaPersistentType getContextPersistentType(JpaProject jpaProject, 
													   String fullyQualifiedTypeName) {
		
		PersistenceUnit unit = getPersistenceUnit(jpaProject);
		for (ClassRef ref : unit.getClassRefs()) {
			JavaPersistentType jpt = ref.getJavaPersistentType(); 
			if ((jpt != null) && jpt.getName().equals(fullyQualifiedTypeName)) {
				return ref.getJavaPersistentType();
			}
		} 
		return null;
	}
	
	/*
	 * Return name of the entity from @Entity(name="...").
	 * If there is no such annotation, returns the 
	 * fully qualified name of the class
	 */
	public String getEntityName(JavaPersistentType jpt) {
		if (jpt == null)
			return "";	//$NON-NLS-1$
		JavaResourceType jrpt = convertJPTToJRT(jpt);
		if (jrpt == null) 
			return "";	//$NON-NLS-1$
		
		String name = null;
		JavaTypeMapping mapping = jpt.getMapping();
		if ((mapping != null) && JavaEntity.class.isInstance(mapping))
			name = ((JavaEntity)mapping).getSpecifiedName();
		if (name == null)
			name = jpt.getName();
		return name;
	}
								
	public boolean hasNameAnnotation(JavaPersistentType jpt) {
		if (jpt == null)
			return false;
		JavaResourceType jrpt = convertJPTToJRT(jpt);
		if (jrpt == null)
			return false;
		JavaTypeMapping jtm = jpt.getMapping();
		if (jtm == null)
			return false;
		if (jtm instanceof JavaEntity) {
			JavaEntity mapping = (JavaEntity)jtm;
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
	public boolean hasAnyAnnotationType(JavaPersistentType jpt) {
		return hasEntityAnnotation(jpt) || hasMappedSuperclassAnnotation(jpt) || hasEmbeddableAnnotation(jpt); 
	}
	
	/**
	 * Checks whether the persistent type is of entity type
	 * @param jpt
	 * @return true, if the given jpt is an entity, false otherwise
	 */
	public boolean hasEntityAnnotation(JavaPersistentType jpt) {
		return (jpt.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY);
	}	
	
	/**
	 * Checks whether the persistent type is of mapped superclass type.
	 * @param jpt
	 * @return true, if the given jpt is a mapped superclass, false, otherwise.
	 */
	public boolean hasMappedSuperclassAnnotation(JavaPersistentType jpt) {
		return (jpt.getMappingKey() == MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
	}
	
	/**
	 * Checks whether the persistent type is of an embeddable type.
	 * @param jpt
	 * @return true, if the given jpt is an embeddable class, false otherwise.
	 */
	public boolean hasEmbeddableAnnotation(JavaPersistentType jpt){
		return (jpt.getMappingKey() == MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
	}
	
	public String getSpecifiedEntityName(JavaPersistentType jpt){
		JavaTypeMapping jtm = jpt.getMapping();
		if (jtm instanceof JavaEntity) {
			JavaEntity gje = (JavaEntity) jtm;
			return gje.getSpecifiedName();
		}
		return jtm.getName();
	}
	
	public void renameEntity(JavaPersistentType jpt, String newName) {
		JavaTypeMapping jtm = jpt.getMapping();
		if (jtm instanceof JavaEntity) {
			JavaEntity gje = (JavaEntity) jtm;
			gje.setSpecifiedName(newName);
		}
	}
	
	/**
	 * Find the first {@link HasReferenceRelation} for the given embeddable class from all existing
	 * {@link HasReferanceRelation} in the diagram. 
	 * @param embeddable - the given embeddable class
	 * @param fp
	 * @return the first {@link HasReferenceRelation} for the given embeddable class.
	 */
	public HasReferanceRelation findFisrtHasReferenceRelationByEmbeddable(JavaPersistentType embeddable, IJPAEditorFeatureProvider fp){
		HashSet<HasReferanceRelation> hasReferencesConnections = fp.getAllExistingHasReferenceRelations();
		for(HasReferanceRelation ref : hasReferencesConnections){
			if (ref.getEmbeddable().equals(embeddable) && hasEntityAnnotation(ref.getEmbeddingEntity())){
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
	public HashSet<JavaPersistentType> findAllJPTWithTheGivenEmbeddedId(JavaPersistentType embeddable, IJPAEditorFeatureProvider fp){
		HashSet<JavaPersistentType> embeddingEntities = new HashSet<JavaPersistentType>();
		if(!hasEmbeddableAnnotation(embeddable))
			return embeddingEntities;
		ListIterator<PersistenceUnit> lit = embeddable.getJpaProject().getContextModelRoot().getPersistenceXml().getRoot().getPersistenceUnits().iterator();		
		PersistenceUnit pu = lit.next();
		for(PersistentType jpt : pu.getPersistentTypes()){
			if(!jpt.equals(embeddable)){
				for(JavaPersistentAttribute jpa : ((JavaPersistentType)jpt).getAttributes()){
					if(isEmbeddedId(jpa) && JPAEditorUtil.getAttributeTypeNameWithGenerics(jpa).equals(embeddable.getName())){
						embeddingEntities.add((JavaPersistentType) jpt);
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
	public Set<HasReferanceRelation> findAllHasReferenceRelationsByEmbeddable(JavaPersistentType embeddable, IJPAEditorFeatureProvider fp){
		HashSet<HasReferanceRelation> allHasRefForEmbeddable = new HashSet<HasReferanceRelation>();
		HashSet<HasReferanceRelation> hasReferencesConnections = fp.getAllExistingHasReferenceRelations();
		for(HasReferanceRelation ref : hasReferencesConnections){
			if (ref.getEmbeddable().equals(embeddable) || ref.getEmbeddingEntity().equals(embeddable)){
				allHasRefForEmbeddable.add(ref);
			}
		}
		return allHasRefForEmbeddable;
	}
	
	public Set<HasReferanceRelation> findAllHasReferenceRelsByEmbeddableWithEntity(JavaPersistentType embeddable, IJPAEditorFeatureProvider fp){
		Set<HasReferanceRelation> allRefs = findAllHasReferenceRelationsByEmbeddable(embeddable, fp);
		Set<HasReferanceRelation> entityRefs = new HashSet<HasReferanceRelation>();
		for(HasReferanceRelation ref : allRefs){
			if(hasEntityAnnotation(ref.getEmbeddingEntity())){
				entityRefs.add(ref);
			}
		}
		
		return entityRefs;
	}
	
	/**
	 * Create a relationship attribute.
	 * @param fp
	 * @param jpt - the referencing {@link JavaPersistentType}
	 * @param attributeType - the referenced {@link JavaPersistentType}
	 * @param mapKeyType
	 * @param attributeName - the name of the attribute
	 * @param actName - the actual name of the attribute
	 * @param isCollection - whether the attribute is of a collection type
	 * @param cu - the {@link ICompilationUnit} of the referencing {@link JavaPersistentType}
	 * @return the newly created relationship attribute.
	 */
	public JavaPersistentAttribute addAttribute(IJPAEditorFeatureProvider fp, JavaPersistentType jpt, 
			JavaPersistentType attributeType, String mapKeyType, String attributeName,
			String actName, boolean isCollection, ICompilationUnit cu) {
				
		try {
			if (doesAttributeExist(jpt, actName)) {
				return (JavaPersistentAttribute) jpt.resolveAttribute(attributeName);
			}
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannnot create a new attribute with name " + attributeName, e); //$NON-NLS-1$				
		}
		
		JavaPersistentAttribute res = makeNewAttribute(fp, jpt, cu, attributeName, attributeType.getName(), actName, mapKeyType, null, null, isCollection);
		
		return res;
	}
	
	/**
	 * Checks whether an entity's attribute is of a collection type
	 * @param entityShape - the pictogram element for the entity in the diagram
	 * @param fp
	 * @param attrTxt - the attribute name
	 * @return true, if the entity's attribute is of a collection type, false otherwise.
	 */
	public boolean isCollection(ContainerShape entityShape,
			IJPAEditorFeatureProvider fp, String attrTxt) {
		Object ob = fp.getBusinessObjectForPictogramElement(entityShape);
		if(ob instanceof JavaPersistentType){
			JavaPersistentType jpt = (JavaPersistentType) ob;
			ICompilationUnit cu = fp.getCompilationUnit(jpt);
				IType type = cu.getType(JPAEditorUtil.returnSimpleName(jpt.getName()));
			IField field = type.getField(attrTxt);
			if (field.exists()) {
				try {
					if(field.getTypeSignature().contains("List") || field.getTypeSignature().contains("Set") //$NON-NLS-1$ //$NON-NLS-2$
							|| field.getTypeSignature().contains("Collection") || field.getTypeSignature().contains("Map") //$NON-NLS-1$ //$NON-NLS-2$
							|| field.getTypeSignature().endsWith("[]")) //$NON-NLS-1$
						return true;
				} catch (JavaModelException e) {
					JPADiagramEditorPlugin.logError(JPAEditorMessages.JpaArtifactFactory_CanNotCheckReturnType +
							" " + attrTxt + "\"", e); //$NON-NLS-1$		//$NON-NLS-2$		
				}				
			}
		}
		return false;
	}

	/**
	 * Checks whether the getter method of an entity's attribute returns a collection type.
	 * @param entityShape - the pictogram element for the entity in the diagram
	 * @param fp
	 * @param attrTxt - the attribute name
	 * @return true if the getter method of an attribute returns a collection type,
	 * false otherwise.
	 */
	public boolean isGetterMethodReturnTypeCollection(
			ContainerShape entityShape, IJPAEditorFeatureProvider fp,
			String attrTxt) {
		Object ob = fp.getBusinessObjectForPictogramElement(entityShape);
		if(ob instanceof JavaPersistentType){
		JavaPersistentType jpt = (JavaPersistentType) ob;
		ICompilationUnit cu = fp.getCompilationUnit(jpt);
			IType type = cu.getType(JPAEditorUtil.returnSimpleName(jpt.getName()));
			String attrNameWithCapitalLetter = attrTxt.substring(0, 1)
					.toUpperCase(Locale.ENGLISH)
					+ attrTxt.substring(1);
		String methodName = "get" + attrNameWithCapitalLetter;  //$NON-NLS-1$
		IMethod method = type.getMethod(methodName, new String[0]);
		try {
			if(method.getReturnType().contains("List") || method.getReturnType().contains("Set") //$NON-NLS-1$ //$NON-NLS-2$
					|| method.getReturnType().contains("Collection") || method.getReturnType().contains("Map") //$NON-NLS-1$ //$NON-NLS-2$
					|| method.getReturnType().endsWith("[]")) //$NON-NLS-1$
			  return true;
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError(JPAEditorMessages.JpaArtifactFactory_CanNotCheckReturnType + 
					" " + attrTxt + "\"", e); //$NON-NLS-1$		//$NON-NLS-2$		
		}
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
	public String createNewAttribute(JavaPersistentType jpt, 
			boolean isCollection, IJPAEditorFeatureProvider fp) {
		
		String attrTypeName = "java.lang.String"; 																	//$NON-NLS-1$
		String newAttrName = genUniqueAttrName(jpt, attrTypeName, fp);
		ICompilationUnit cu = fp.getCompilationUnit(jpt);
		makeNewAttribute(fp, jpt, cu, newAttrName, attrTypeName, newAttrName, attrTypeName, null, null, isCollection);
		return newAttrName;
	}

	public JavaPersistentAttribute makeNewAttribute(IJPAEditorFeatureProvider fp, JavaPersistentType jpt, ICompilationUnit cu, String attrName, String attrTypeName,
			String actName, String mapKeyType, String[] attrTypes, List<String> annotations, boolean isCollection) {

		if(cu == null){
			cu = fp.getCompilationUnit(jpt);
		}
		
		Command createNewAttributeCommand = new AddAttributeCommand(fp, jpt, attrTypeName, mapKeyType, attrName, actName, attrTypes, annotations, isCollection, cu);
		try {
			getJpaProjectManager().execute(createNewAttributeCommand, SynchronousUiCommandExecutor.instance());
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot create a new attribute with name " + attrName, e); //$NON-NLS-1$		
		}

		JavaPersistentAttribute jpa = jpt.getAttributeNamed(attrName);
		if(jpa == null){
			jpa = jpt.getAttributeNamed(actName);
		}
		return jpa;
	}
		
	/**
	 * Delete an attribute from the entity.
	 * @param jpt - the entity from which the attribute will be deleted
	 * @param attributeName - the name of the attribute to be deleted
	 * @param fp
	 */
	public void deleteAttribute(JavaPersistentType jpt, String attributeName,
								IJPAEditorFeatureProvider fp) {
		synchronized (jpt) {
			Command deleteAttributeCommand = new DeleteAttributeCommand(null, jpt, attributeName, fp);
			try {
				getJpaProjectManager().execute(deleteAttributeCommand, SynchronousUiCommandExecutor.instance());
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
	public String genUniqueAttrName(JavaPersistentType jpt, 
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
	 * Get all annotation  for the given attribute.
	 * @param persistentAttribite
	 * @return an array of all current annotations of the given attribute.
	 */
	public Annotation[] getAnnotations(JavaPersistentAttribute persistentAttribite) {	
		JavaResourceAttribute jrpt = persistentAttribite.getResourceAttribute();
		Annotation[] res = new Annotation[jrpt.getAnnotationsSize()];
		int c = 0;
		for (Annotation annotation : jrpt.getAnnotations()) {
			res[c] = annotation;
			c++;
		}
		return res;
	}		
	
	/**
	 * Get all annotations as string for the given attribute.
	 * @param persistentAttribite
	 * @return a set of strings of all current annotation names of the given attribute.
	 */
	public HashSet<String> getAnnotationNames(JavaPersistentAttribute persistentAttribite) {
		HashSet<String> res = new HashSet<String>();
		if(persistentAttribite != null) {
			JavaResourceAttribute jrpt = persistentAttribite.getResourceAttribute();
			for (Annotation annotation : jrpt.getAnnotations()) {
				res.add(JPAEditorUtil.returnSimpleName(annotation.getAnnotationName()));
			}
		}
		return res;
	}	
	
	/**
	 * Get all annotations as string for the given attribute.
	 * @param persistentAttribite
	 * @return a list of strings of all current annotation names of the given attribute.
	 */
	public List<String> getAnnotationStrings(
			JavaPersistentAttribute persistentAttribite) {
		
		JavaPersistentType jpt = (JavaPersistentType)persistentAttribite.getParent();
		CompilationUnit jdtCU = jpt.getJavaResourceType().getJavaResourceCompilationUnit().buildASTRoot();
		JavaResourceAttribute jrpt = persistentAttribite.getResourceAttribute();
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
	private boolean isNonOwner(JavaPersistentAttribute at) {
		JavaAttributeMapping jam = at.getMapping();
		if (!(jam.getMappingAnnotation() instanceof OwnableRelationshipMappingAnnotation))
			return false;
		OwnableRelationshipMappingAnnotation nom = (OwnableRelationshipMappingAnnotation)jam.getMappingAnnotation();
		return nom.getMappedBy() != null;
	}
	
	/**
	 * Collect all relationships for the given {@link JavaPersistentType}.
	 * @param newJPT
	 * @param fp
	 * @return an collection of all relationships for the given {@link JavaPersistentType}.
	 */
	private Collection<IRelation> produceAllIRelations(
			JavaPersistentType newJPT, IJPAEditorFeatureProvider fp) {
		
		Collection<IRelation> res = produceIRelations(newJPT, null, fp);
		Iterator<IRelation> it = res.iterator();
		HashSet<JavaPersistentType> checkedEntities = new HashSet<JavaPersistentType>();
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
			JavaPersistentType jpt = (JavaPersistentType) fp
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
	 * Collect all "has-reference" relationships for the given {@link JavaPersistentType}.
	 * @param newJPT
	 * @param fp
	 * @return an collection of all "has-reference" relationships for the given {@link JavaPersistentType}.
	 */
	public Collection<HasReferanceRelation> produceAllEmbeddedRelations(JavaPersistentType jpt, IJPAEditorFeatureProvider fp) {
		
		Collection<HasReferanceRelation> res = produceEmbRelations(jpt, null, fp);
		Iterator<HasReferanceRelation> it = res.iterator();
		HashSet<JavaPersistentType> checkedEntities = new HashSet<JavaPersistentType>();
		while (it.hasNext()) {
			HasReferanceRelation rel = it.next();
			checkedEntities.add(rel.getEmbeddable());
			checkedEntities.add(rel.getEmbeddingEntity());
		}
		List<Shape> shapes = fp.getDiagramTypeProvider().getDiagram().getChildren();
		Iterator<Shape> iter = shapes.iterator();
		while (iter.hasNext()) {
			Shape sh = iter.next();
			JavaPersistentType embeddingEntity = (JavaPersistentType) fp.getBusinessObjectForPictogramElement(sh);
			if (embeddingEntity == null)
				continue;
			Collection<HasReferanceRelation> rels = produceEmbRelations(embeddingEntity, jpt, fp);
			res.addAll(rels);
		}
		return res;
	 }
	
	/**
	 * Collect all "has-reference" relationships for the given {@link JavaPersistentType}.
	 * @param embeddingEntity
	 * @param embeddable
	 * @param fp
	 * @return an collection of all "has-reference" relationships for the given {@link JavaPersistentType}.
	 */
	private Collection<HasReferanceRelation> produceEmbRelations(JavaPersistentType embeddingEntity,
			JavaPersistentType embeddable, IJPAEditorFeatureProvider fp) {
		
		Collection<HasReferanceRelation> resSet = new HashSet<HasReferanceRelation>();
		HasReferanceRelation res = null;
		for (JavaPersistentAttribute embeddingAttribute : embeddingEntity.getAttributes()) {
			IResource r = embeddingAttribute.getParent().getResource();
			if (!r.exists())
				throw new RuntimeException();
			try {
				JavaResourceAttribute jrpa = embeddingAttribute.getResourceAttribute();
				Annotation[] ans = this.getAnnotations(embeddingAttribute);
				for (Annotation an : ans) {
					String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
					if (JPAEditorConstants.ANNOTATION_EMBEDDED.equals(annotationName) ||
							JPAEditorConstants.ANNOTATION_ELEMENT_COLLECTION.equals(annotationName)
							|| JPAEditorConstants.ANNOTATION_EMBEDDED_ID.equals(annotationName)) {
						String attributeTypeName = getRelTypeName(an, jrpa);
						if(embeddable != null) {
							if (!attributeTypeName.equals(embeddable.getName()))
								continue;
						}
						
						JavaPersistentType embeddableClass = findJPT(embeddingAttribute, fp, an);
						if (embeddableClass != null) {
								res = produceEmbeddedRelation(embeddingAttribute, an, embeddableClass, fp);
								if (res != null)
									resSet.add(res);
						}
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
	public IRelation produceIRelation(JavaPersistentAttribute persistentAttribite, JavaPersistentType jpt2,
			IJPAEditorFeatureProvider fp) {
		
		IRelation res = null;
		Annotation[] ans = getAnnotations(persistentAttribite);
		for (Annotation an : ans) {
			String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
			if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(annotationName)) {
				if(jpt2 != null){
					JavaResourceAttribute jrpa = persistentAttribite.getResourceAttribute();
					String relTypeName = getRelTypeName(an, jrpa);
					if (!relTypeName.equals(jpt2.getName()))
						continue;
				}
				
				JavaPersistentType relJPT = findJPT(persistentAttribite, fp, an);
				if (relJPT != null) {
						res = produceRelation(persistentAttribite, an, relJPT, fp);
				}
			}									
		}
		return res;
				
	}

	/**
	 * Gets the parent {@link JavaPersistentType} of an attribute
	 * @param persistentAttribite
	 * @param fp
	 * @param an - attribute's relationship annotation
	 * @return the parent {@link JavaPersistentType} of an attribute.
	 */
    public JavaPersistentType findJPT(JavaPersistentAttribute persistentAttribite, IJPAEditorFeatureProvider fp, Annotation an) {
    	JavaResourceAttribute jrpa = persistentAttribite.getResourceAttribute();
		String relTypeName = getRelTypeName(an, jrpa);
		JavaPersistentType relJPT = (JavaPersistentType)fp.getBusinessObjectForKey(relTypeName);
		return relJPT;
    }
	    
	private Collection<IRelation> produceIRelations(
			JavaPersistentType newJPT, JavaPersistentType jpt2, IJPAEditorFeatureProvider fp) {
		
		Set<IRelation> res = new HashSet<IRelation>();
		for (JavaPersistentAttribute at : newJPT.getAttributes()) {
			IRelation rel = produceIRelation(at, jpt2, fp);
			if (rel != null)
				res.add(rel);
		}
		return res;
	}
	
	/**
	 * Returns the {@link JavaPersistentType} registered in the {@link PersistenceUnit} with the given name.
	 * @param name
	 * @param pu
	 * @return the {@link JavaPersistentType} registered in the {@link PersistenceUnit} with the given name.
	 */
	public JavaPersistentType getJPT(String name, PersistenceUnit pu) {
		JavaPersistentType jpt = (JavaPersistentType) pu.getPersistentType(name);
		int cnt = 0;
		while ((jpt == null) && (cnt < MAX_NUM_OF_ITERATIONS)) {
			try {
				Thread.sleep(PAUSE_DURATION);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Sleep interrupted", e); //$NON-NLS-1$		
			}
			pu.synchronizeWithResourceModel();
			jpt = (JavaPersistentType)pu.getPersistentType(name);
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
	public Set<JavaPersistentAttribute> getRelatedAttributes(JavaPersistentType jpt) {
		Set<JavaPersistentAttribute> res = new HashSet<JavaPersistentAttribute>();
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
			JavaPersistentType jpt1 = (JavaPersistentType) pu
					.getPersistentType(name);
			if (jpt1 == null)
				continue;
			Set<JavaPersistentAttribute> relAts = getRelAttributes(jpt, jpt1);
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
	private Set<JavaPersistentAttribute> getRelAttributes(JavaPersistentType jpt,
			JavaPersistentType relJPT) {

		Set<JavaPersistentAttribute> res = new HashSet<JavaPersistentAttribute>();
		for (JavaPersistentAttribute at : relJPT.getAttributes()) {
			IResource r = at.getParent().getResource();
			if (!r.exists())
				throw new RuntimeException();
			Annotation[] ans = getAnnotations(at);			
			String annotationName = null;
			for (Annotation an : ans) {
				annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName()); 
				if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(annotationName) || JPAEditorConstants.ANNOTATION_EMBEDDED.equals(annotationName)
						|| JPAEditorConstants.ANNOTATION_ELEMENT_COLLECTION.equals(annotationName)
						|| JPAEditorConstants.ANNOTATION_EMBEDDED_ID.equals(annotationName)) {
					String relTypeName = getRelTypeName(an, at.getResourceAttribute());
					if (!relTypeName.equals(jpt.getName()))
						continue;
					res.add(at);
				}
			}			
		}
		return res;
	}
	
	public void renameEntityClass(JavaPersistentType jpt, String newEntityName, IJPAEditorFeatureProvider fp) {
		
		Command renameEntityCommand = new RenameEntityCommand(jpt, newEntityName, fp);
		try {
			getJpaProjectManager().execute(renameEntityCommand);
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot rename entity " + jpt.getName(), e); //$NON-NLS-1$		
		}
	}

	public JavaPersistentAttribute renameAttribute(JavaPersistentType jpt,
			String oldName, String newName, String inverseEntityName,
									 IJPAEditorFeatureProvider fp) throws InterruptedException {
		newName = JPAEditorUtil.decapitalizeFirstLetter(newName);
		if (isMethodAnnotated(jpt)) {		
			newName = JPAEditorUtil.produceValidAttributeName(newName);
		} 
		newName = JPAEditorUtil.produceUniqueAttributeName(jpt, newName);
		PersistenceUnit pu = null;
		JavaPersistentAttribute oldAt = jpt.getAttributeNamed(oldName);
		fp.addAddIgnore((JavaPersistentType)oldAt.getParent(), newName);
		JavaResourceAttribute jra = oldAt
				.getResourceAttribute();
		fp.addRemoveIgnore((JavaPersistentType)oldAt.getParent(), jra.getName());

		Command renameAttributeCommand = new RenameAttributeCommand(null, jpt, oldName, newName, fp);
		getJpaProjectManager().execute(renameAttributeCommand, SynchronousUiCommandExecutor.instance());
		
		JavaPersistentAttribute newAt = jpt.getAttributeNamed(newName);
		if (newAt == null) {
			JPADiagramEditorPlugin.logError("The attribute " + newName + " could not be resolved", new NullPointerException()); //$NON-NLS-1$  //$NON-NLS-2$
		}
		
		fp.addRemoveIgnore(jpt, oldAt.getName());
		try {
			fp.replaceAttribute(oldAt, newAt);
		} catch (Exception e) {
			return newAt;
		}
		
		updateIRelationshipAttributes(jpt, inverseEntityName, fp, pu, oldAt,
				newAt);
		
		return newAt;
	}

	private void updateIRelationshipAttributes(JavaPersistentType jpt,
			String inverseEntityName, IJPAEditorFeatureProvider fp,
			PersistenceUnit pu, JavaPersistentAttribute oldAt,
			JavaPersistentAttribute newAt) throws InterruptedException {
		IRelation rel = fp.getRelationRelatedToAttribute(oldAt);
		String inverseAttributeName = null;
		JavaPersistentType inverseJPT = null;
		if (IBidirectionalRelation.class.isInstance(rel)) {
			inverseJPT = rel.getInverse();
			if (inverseJPT != oldAt.getParent()) {
				pu = JpaArtifactFactory.INSTANCE.getPersistenceUnit(jpt);
				inverseAttributeName = rel.getInverseAttributeName();
			}
		}
		
		if (inverseAttributeName != null) {
			Command changeMappedByValueCommand = new SetMappedByNewValueCommand(fp, pu, inverseEntityName, inverseAttributeName, newAt, oldAt, rel);
			getJpaProjectManager().execute(changeMappedByValueCommand, SynchronousUiCommandExecutor.instance());
		}
		if (rel != null) {
			updateRelation(jpt, fp, rel);
			if(hasIDClassAnnotation(jpt)) {
				String idClassFQN = getIdType(jpt);
				IJavaProject javaProject = JavaCore.create(jpt.getJpaProject().getProject());
				IType type = getType(javaProject, idClassFQN);
				if(type != null && type.getField(oldAt.getName()).exists()){
					Command renameAttributeCommand = new RenameAttributeCommand(type.getCompilationUnit(), null, oldAt.getName(), newAt.getName(), fp);
					getJpaProjectManager().execute(renameAttributeCommand, SynchronousUiCommandExecutor.instance());
				}
			}
		}
		
		HashSet<JavaPersistentType> embeddingEntities = findAllJPTWithTheGivenEmbeddedId(jpt, fp);
		if(embeddingEntities != null && !embeddingEntities.isEmpty()){
			renameMapsIdAnnotationValue(oldAt, newAt, embeddingEntities);
		}
		
	}

	private void renameMapsIdAnnotationValue(JavaPersistentAttribute oldAt,
			JavaPersistentAttribute newAt, HashSet<JavaPersistentType> embeddingEntities) {
		
		for(JavaPersistentType embeddingEntity : embeddingEntities){
			for(JavaPersistentAttribute attr : embeddingEntity.getAttributes()){
				Annotation ann = attr.getResourceAttribute().getAnnotation(MapsId2_0Annotation.ANNOTATION_NAME);
				if(ann != null){
					MapsId2_0Annotation mapsIdAnn = (MapsId2_0Annotation) ann;
					if(mapsIdAnn.getValue() != null && mapsIdAnn.getValue().equals(oldAt.getName())){
						((MapsId2_0Annotation)ann).setValue(newAt.getName());
						embeddingEntity.synchronizeWithResourceModel();
					}
				}
			}
		}
	}
	
	
	
	private JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}

	private void updateRelation(JavaPersistentType jpt,
			IJPAEditorFeatureProvider fp, IRelation rel) {
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
	private IRelation produceRelation(JavaPersistentAttribute persistentAttribite, Annotation an,
			JavaPersistentType relJPT, IJPAEditorFeatureProvider fp) {

		Hashtable<JavaPersistentAttribute, Annotation> ht = getRelAttributeAnnotation(
				persistentAttribite, relJPT, fp);
		if (ht == null) {
			return produceUniDirRelation((JavaPersistentType)persistentAttribite
					.getParent(), persistentAttribite, an, relJPT, fp);
		} else {
			JavaPersistentAttribute relAt = ht.keys().nextElement();
			Annotation relAn = ht.get(relAt);
			return produceBiDirRelation((JavaPersistentType)persistentAttribite
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
	private HasReferanceRelation produceEmbeddedRelation(JavaPersistentAttribute embeddingAttribute, Annotation an,
			JavaPersistentType embeddable, IJPAEditorFeatureProvider fp) {
		
		if (!JPAEditorUtil.getCompilationUnit((JavaPersistentType) embeddingAttribute.getParent()).exists())
			return null;
		JavaPersistentType embeddingEntity = (JavaPersistentType) embeddingAttribute.getParent();
		String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
		String embeddedAttributeName = embeddingAttribute.getName();
		HasReferanceRelation res = null;
		if (annotationName.equals(JPAEditorConstants.ANNOTATION_EMBEDDED) || JPAEditorConstants.ANNOTATION_EMBEDDED_ID.equals(annotationName)) {
			if (!fp.doesEmbeddedRelationExist(embeddable, embeddingEntity, embeddedAttributeName, HasReferenceType.SINGLE)) {
				res = new HasSingleReferenceRelation(embeddingEntity, embeddable);
			}
		} else if (annotationName.equals(JPAEditorConstants.ANNOTATION_ELEMENT_COLLECTION)) {
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
	 * @param relJPT - inverse {@link JavaPersistentType}.
	 * @param fp
	 * @return a {@link Hashtable} containing the pair: inverseAttribute <-> relation annotation.
	 */
	private Hashtable<JavaPersistentAttribute, Annotation> getRelAttributeAnnotation(
			JavaPersistentAttribute jpa, JavaPersistentType relJPT, IJPAEditorFeatureProvider fp) {
		
		JavaPersistentType jpt = (JavaPersistentType)jpa.getParent();
		for (JavaPersistentAttribute relEntAt : relJPT.getAttributes())	{
			IResource r = relEntAt.getParent().getResource();
			if (!r.exists())
				throw new RuntimeException();
			JavaResourceAttribute relJRA = relEntAt.getResourceAttribute();
			Annotation[] ans = this.getAnnotations(relEntAt);
			for (Annotation an : ans) {
				String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
				if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(annotationName)) {

					JavaAttributeMapping mp = relEntAt.getMapping();
					if (!OwnableRelationshipMappingAnnotation.class.isInstance(mp.getMappingAnnotation()))
						continue;
					
					if(an instanceof OwnableRelationshipMappingAnnotation) {
						String mappedByAttr = ((OwnableRelationshipMappingAnnotation)an).getMappedBy();
						if(mappedByAttr == null)
							continue;
						
						String[] mappedByStrings = mappedByAttr.split(MAPPED_BY_ATTRIBUTE_SPLIT_SEPARATOR);
						if(mappedByStrings.length>1){
							jpt = getInvolvedEntity(fp, jpt, relEntAt, an, mappedByStrings);	
						} else {
							String mappedBy = ((OwnableRelationshipMappingAnnotation)mp.getMappingAnnotation()).getMappedBy();
							if (!jpa.getName().equals(mappedBy)) 
								continue;
						}
					}
					
					String relTypeName = getRelTypeName(an, relJRA);					
					if (!relTypeName.equals(jpt.getName())) 
						continue;
					Hashtable<JavaPersistentAttribute, Annotation> ht = new Hashtable<JavaPersistentAttribute, Annotation>();
					ht.put(relEntAt, an);
					return ht;					
				}
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
	 * @param an - the relation attribute annotation
	 * @param mappedByStrings - all strings in the mappedBy attribute
	 * @return the entity involved in the bidirectional relationship between the embeddable class and some entity.
	 */
	private JavaPersistentType getInvolvedEntity(IJPAEditorFeatureProvider fp, JavaPersistentType jpt,
			JavaPersistentAttribute relEntAt, Annotation an, String[] mappedByStrings) {
		String mappedBy = mappedByStrings[0];
		JavaPersistentType involvedEntity = findJPT(relEntAt, fp, an);
		JavaPersistentAttribute embeddedAttribute = involvedEntity.getAttributeNamed(mappedBy);
		if(embeddedAttribute != null){
			JavaPersistentType embeddedJPT = findJPT(embeddedAttribute, fp, getAnnotations(embeddedAttribute)[0]);
			if(embeddedJPT.equals(jpt)) {
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
			JavaPersistentType jpt, JavaPersistentAttribute at, Annotation an,
			JavaPersistentType relJPT, IJPAEditorFeatureProvider fp) {
		
		if (isNonOwner(at) || !JPAEditorUtil.getCompilationUnit((JavaPersistentType) at.getParent()).exists())
			return null;
		String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
		IUnidirectionalRelation res = createUniDirRelationshipDependsOnTheType(jpt, at, relJPT, fp, annotationName);
		if (res != null)
			res.setAnnotatedAttribute(at);
		return res;
	}

	private IUnidirectionalRelation createUniDirRelationshipDependsOnTheType(
			JavaPersistentType jpt, JavaPersistentAttribute at,
			JavaPersistentType relJPT, IJPAEditorFeatureProvider fp,
			String annotationName) {
		IUnidirectionalRelation res = null;
		String attrName = at.getName();
		if (annotationName.equals(JPAEditorConstants.ANNOTATION_ONE_TO_ONE)) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, null, RelType.ONE_TO_ONE,
					RelDir.UNI))
				res = new OneToOneUniDirRelation(fp, jpt, relJPT, attrName, false, false);
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_ONE_TO_MANY)) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, null, RelType.ONE_TO_MANY,
					RelDir.UNI))
				res = new OneToManyUniDirRelation(fp, jpt, relJPT, attrName, false);
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_MANY_TO_ONE)) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, null, RelType.MANY_TO_ONE,
					RelDir.UNI))
				res = new ManyToOneUniDirRelation(fp, jpt, relJPT, attrName, false, false);
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_MANY_TO_MANY)) {
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
	private IBidirectionalRelation produceBiDirRelation(JavaPersistentType jpt,
			JavaPersistentAttribute at, Annotation an,
			JavaPersistentType relJPT, JavaPersistentAttribute relAt,
			Annotation relAn, IJPAEditorFeatureProvider fp) {
		String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
		String relAnnotationName = JPAEditorUtil.returnSimpleName(relAn.getAnnotationName());
		if (!annotationNamesMatch(annotationName, relAnnotationName)) 
			return null;
		if (annotationName.equals(JPAEditorConstants.ANNOTATION_ONE_TO_MANY)) 
			return produceBiDirRelation(relJPT, relAt, relAn, jpt, at, an, fp);
		if (isNonOwner(at) && isNonOwner(relAt)) 
			return null;
		if (annotationName.equals(JPAEditorConstants.ANNOTATION_MANY_TO_ONE)) {
			if (isNonOwner(at) || !isNonOwner(relAt)) 
				return null;
		}
		if (annotationName.equals(JPAEditorConstants.ANNOTATION_MANY_TO_MANY)) {
			if (!isNonOwner(at) && !isNonOwner(relAt)) 
				return null;
			if (isNonOwner(at)) 
				return produceBiDirRelation(relJPT, relAt, relAn, jpt, at, an,
						fp);
		}		
		String ownerAttrName = at.getName();
		String inverseAttrName = relAt.getName();
		
		JavaAttributeMapping m = relAt.getMapping();
		
		if ((m != null)){
			if(m.getMappingAnnotation() instanceof OwnableRelationshipMappingAnnotation) {
		
			String mappedBy = ((OwnableRelationshipMappingAnnotation)m.getMappingAnnotation()).getMappedBy();
			if (mappedBy == null)
				return null;
			String[] attrs = mappedBy.split(MAPPED_BY_ATTRIBUTE_SPLIT_SEPARATOR);
			if(attrs.length > 1) {
				mappedBy = attrs[1];
			}
			if (!mappedBy.equals(ownerAttrName))
				return null;
			}
		}
		
		IBidirectionalRelation res = createBiDirRelationshipDependsOnTheType(
				jpt, relJPT, fp, annotationName, ownerAttrName, inverseAttrName);
		if (res != null) {
			res.setOwnerAnnotatedAttribute(at);
			res.setInverseAnnotatedAttribute(relAt);
		}
		return res;
	}

	private IBidirectionalRelation createBiDirRelationshipDependsOnTheType(
			JavaPersistentType jpt, JavaPersistentType relJPT,
			IJPAEditorFeatureProvider fp, String annotationName,
			String ownerAttrName, String inverseAttrName) {
		IBidirectionalRelation res = null;
		if (annotationName.equals(JPAEditorConstants.ANNOTATION_ONE_TO_ONE)) {
			if (!fp.doesRelationExist(jpt, relJPT, ownerAttrName, inverseAttrName, RelType.ONE_TO_ONE,
					RelDir.BI))
				res = new OneToOneBiDirRelation(fp, jpt, relJPT, ownerAttrName,
						inverseAttrName, false, relJPT, false);
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_MANY_TO_ONE)) {
			if (!fp.doesRelationExist(jpt, relJPT, ownerAttrName, inverseAttrName, RelType.MANY_TO_ONE,
					RelDir.BI))
				res = new ManyToOneBiDirRelation(fp, jpt, relJPT, ownerAttrName,
						inverseAttrName, false, relJPT, false);
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_MANY_TO_MANY)) {
			if (!fp.doesRelationExist(jpt, relJPT, ownerAttrName, inverseAttrName, RelType.MANY_TO_MANY,
					RelDir.BI))
				res = new ManyToManyBiDirRelation(fp, jpt, relJPT, ownerAttrName,
						inverseAttrName, false, relJPT);
		}
		return res;
	}
	
	private boolean annotationNamesMatch(String an1Name, String an2Name) {
		if (an1Name.equals(JPAEditorConstants.ANNOTATION_ONE_TO_ONE)
				&& an2Name.equals(JPAEditorConstants.ANNOTATION_ONE_TO_ONE)) {
			return true;
		} else if (an1Name.equals(JPAEditorConstants.ANNOTATION_ONE_TO_MANY)
				&& an2Name.equals(JPAEditorConstants.ANNOTATION_MANY_TO_ONE)) {
			return true;
		} else if (an1Name.equals(JPAEditorConstants.ANNOTATION_MANY_TO_ONE)
				&& an2Name.equals(JPAEditorConstants.ANNOTATION_ONE_TO_MANY)) {
			return true;
		} else if (an1Name.equals(JPAEditorConstants.ANNOTATION_MANY_TO_MANY)
				&& an2Name.equals(JPAEditorConstants.ANNOTATION_MANY_TO_MANY)) {
			return true;
		}
		return false;
	}
	
	private boolean doesAttributeExist(JavaPersistentType jpt, String name)
											throws JavaModelException {
		boolean exists = false;
		if (jpt.resolveAttribute(name) != null) {
			return true;
		}
		return exists;
	}
		
	public JavaResourceType convertJPTToJRT(JavaPersistentType jpt) {
		if (jpt == null) 
			return null;
		return (JavaResourceType) jpt.getJpaProject().getJavaResourceType(jpt.getName(), AstNodeType.TYPE);
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
	
	public PersistenceUnit getPersistenceUnit(JavaPersistentType jpt) {
		return jpt.getPersistenceUnit(); 
	}
	
	public boolean isMethodAnnotated(JavaPersistentAttribute attr) {
		return attr.getResourceAttribute().getAstNodeType() == AstNodeType.METHOD;
	}
	
	public boolean isMethodAnnotated(JavaPersistentType jpt) {
		ListIterator<JavaPersistentAttribute> li = jpt.getAttributes().iterator();
		if (!li.hasNext())
			return false;
		return (isMethodAnnotated(li.next()));
	}
	
	public void remakeRelations(IJPAEditorFeatureProvider fp,
			ContainerShape cs, JavaPersistentType jpt) {
		if (cs == null) 
			cs = (ContainerShape)fp.getPictogramElementForBusinessObject(jpt);
		if (cs == null)
			return;
		removeOldRelations(fp, cs);
		addNewRelations(fp, jpt);
	}
	
	public String getTableName(JavaPersistentType jpt) {
		if (jpt == null)
			return null; 
		JavaResourceType jrt = convertJPTToJRT(jpt);
		if (jrt == null)
			return null; 
		TableAnnotation tan = (TableAnnotation)jrt.getAnnotation("javax.persistence.Table"); //$NON-NLS-1$
		String tableName = null;
		if (tan == null){
			tableName = JPAEditorUtil.returnSimpleName(jpt.getName());
		} else {
			tableName = tan.getName();		
		}		
		if (tableName == null)
			tableName = JPAEditorUtil.returnSimpleName(jpt.getName());
		return tableName;
	}
	
	
	public void setTableName(JavaPersistentType jpt, String tableName) {
		if (jpt == null)
			return; 
		JavaResourceType jrt = convertJPTToJRT(jpt);
		if (jrt == null) {
			return; 
		}
		TableAnnotation ta = (TableAnnotation)jrt.getAnnotation("javax.persistence.Table");	//$NON-NLS-1$
		if (ta != null) 
			ta.setName(tableName);		
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
			JavaPersistentType jpt) {
		addIRelationships(fp, jpt);
		addEmbeddedRelation(fp, jpt);
		rearrangeIsARelations(fp);
	}

	private void addEmbeddedRelation(IJPAEditorFeatureProvider fp,
			JavaPersistentType jpt) {
		Collection<HasReferanceRelation> newEmbeddedRels = produceAllEmbeddedRelations(jpt, fp);
		Iterator<HasReferanceRelation> relationIterator = newEmbeddedRels.iterator();
		while (relationIterator.hasNext()) {
			HasReferanceRelation rel = relationIterator.next();
			addNewEmbeddedRelation(fp, rel);
		}
	}

	private void addIRelationships(IJPAEditorFeatureProvider fp,
			JavaPersistentType jpt) {
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
	
	private String getRelTypeName(Annotation an,
			JavaResourceAttribute jra) {
		String relTypeName = null;
		try {
			boolean isMap = jra.getTypeBinding().getQualifiedName().equals(JPAEditorConstants.MAP_TYPE);
			relTypeName = jra.getTypeBinding().getTypeArgumentName(isMap ? 1 : 0);
		} catch (Exception e) {}
		if (relTypeName == null && an != null && (an instanceof RelationshipMappingAnnotation)) 
			relTypeName = ((RelationshipMappingAnnotation)an).getFullyQualifiedTargetEntityClassName();												
		if (relTypeName == null)
			relTypeName = JPAEditorUtil.getAttributeTypeName(jra);							
		return relTypeName;
	}
		
	public JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}
	
	public boolean hasIDClassAnnotation(JavaPersistentType jpt){
		Annotation an = jpt.getJavaResourceType().getAnnotation(IdClassAnnotation.ANNOTATION_NAME);
		if(an != null){
			return true;
		}
		return false;
	}
	
	public String getIdType(JavaPersistentType jpt) {
		IdClassAnnotation an = (IdClassAnnotation)jpt.getJavaResourceType().getAnnotation(IdClassAnnotation.ANNOTATION_NAME);
		if (an != null)
			return an.getFullyQualifiedClassName();
		JavaPersistentAttribute[] ids = getIds(jpt);
		if (ids.length == 0)
			return null;
		String type = ids[0].getTypeName();
		String wrapper = JPAEditorUtil.getPrimitiveWrapper(type);
		return (wrapper != null) ? wrapper : type;
	}
	
	public JavaPersistentAttribute[] getIds(JavaPersistentType jpt) {
		ArrayList<JavaPersistentAttribute> res = new ArrayList<JavaPersistentAttribute>();
		for (JavaPersistentAttribute at : jpt.getAttributes()) {
			if (isId(at)) {
				res.add(at);
			}
		}
		JavaPersistentAttribute[] ret = new JavaPersistentAttribute[res.size()];
		return res.toArray(ret);
	}
	
	// returns true even if the primary key is inherited
	public boolean hasOrInheritsPrimaryKey(JavaPersistentType jpt) {
		Iterable<ReadOnlyPersistentAttribute> attributes = jpt.getAllAttributes();
		Iterator<ReadOnlyPersistentAttribute> it = attributes.iterator();
		while (it.hasNext()) {
			ReadOnlyPersistentAttribute at = it.next();
			if (isId(at))
				return true;
		}
		return false;
	}
	
	public boolean hasPrimaryKey(JavaPersistentType jpt) {
		for (JavaPersistentAttribute at : jpt.getAttributes()) 
			if (isId(at)) return true;
		return false;
	}

	private boolean hasSimplePk(JavaPersistentType jpt) {
		for(JavaPersistentAttribute at : jpt.getAttributes()){
			if(isSimpleId(at) && !hasIDClassAnnotation(jpt)){
				return true;
			}
		}
		return false;
	}
	
	private JavaPersistentAttribute getSimplePkAttribute(JavaPersistentType jpt){
		for(JavaPersistentAttribute jpa : jpt.getAttributes()){
			if(isSimpleId(jpa)){
				return jpa;
			}
		}
		return null;
	}
	
	public boolean isId(ReadOnlyPersistentAttribute jpa) {
		return isSimpleId(jpa) || isEmbeddedId(jpa);
	}
	
	public boolean isSimpleId(ReadOnlyPersistentAttribute jpa) {
		return (jpa.getMappingKey() == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
	}
	
	private boolean hasEmbeddedPk(JavaPersistentType jpt){
		for(JavaPersistentAttribute at : jpt.getAttributes()){
			if(isEmbeddedId(at)){
				return true;
			}
		}
		return false;
	}
	
	private JavaPersistentAttribute getEmbeddedIdAttribute(JavaPersistentType jpt){
		for(JavaPersistentAttribute jpa : jpt.getAttributes()){
			if(isEmbeddedId(jpa)){
				return jpa;
			}
		}
		return null;
	}
	
	public boolean isEmbeddedId(ReadOnlyPersistentAttribute jpa) {
		return (jpa.getMappingKey() == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
	}
	
	public boolean isEmbeddedAttribute(JavaPersistentAttribute jpa) {
		return (jpa.getMappingKey() == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
	}
	
	public String getColumnName(JavaPersistentAttribute jpa) {
		String columnName= null;
		ColumnAnnotation an = (ColumnAnnotation)jpa.
									getResourceAttribute().
										getAnnotation(ColumnAnnotation.ANNOTATION_NAME);
		if (an != null) 
			columnName = an.getName();
		if (columnName == null) 
			columnName = jpa.getName();		
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
	
	public JPAEditorConstants.DIAGRAM_OBJECT_TYPE determineDiagramObjectType(JavaPersistentType jpt) {
		if (this.hasEntityAnnotation(jpt)) {
			return JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Entity;
		} else if (this.hasMappedSuperclassAnnotation(jpt)) {
			return JPAEditorConstants.DIAGRAM_OBJECT_TYPE.MappedSupeclass;
		} else if (this.hasEmbeddableAnnotation(jpt)){
			return JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Embeddable;
		}
		throw new IllegalArgumentException();
	}
	
	public String generateIdName(JavaPersistentType jpt) {
		String name = "id";		//$NON-NLS-1$
		String genName = name;
		for (int i = 0; i < 10000000; i++) {
			if (!hasAttributeNamed(jpt, genName))
				return genName;
			genName = name + "_" + i;	//$NON-NLS-1$
		}
		return genName;
	}
	
	
	private  boolean hasAttributeNamed(JavaPersistentType jpt, String name) {
		Iterable<String> hier = jpt.getAllAttributeNames();
		Iterator<String> it = hier.iterator();
		while (it.hasNext()) {
			String atName = it.next();
			if (name.equals(atName))
				return true;
		}
		return false;
	}
	
	public String getMappedSuperclassPackageDeclaration(JavaPersistentType jpt) throws JavaModelException {
		String packageName = null;
		IPackageDeclaration[] packages = JPAEditorUtil.getCompilationUnit(jpt)
				.getPackageDeclarations();
		if (packages.length > 0) {
			IPackageDeclaration packageDecl = packages[0];
			packageName = packageDecl.getElementName();
		}
		return packageName;
	}

	public void buildHierarchy(JavaPersistentType superclass, JavaPersistentType subclass, boolean build) {
	
		Command createNewAttributeCommand = new CreateEntityTypeHierarchy(superclass, subclass, build);
		try {
			getJpaProjectManager().execute(createNewAttributeCommand, SynchronousUiCommandExecutor.instance());
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
	public void calculateDerivedIdAnnotation(JavaPersistentType ownerJPT, JavaPersistentType inverseJPT, JavaPersistentAttribute ownerAttr) {
		String attributeType = null;
		if(hasSimplePk(inverseJPT)){
			JavaPersistentAttribute jpa = getSimplePkAttribute(inverseJPT);
			attributeType  = JPAEditorUtil.getAttributeTypeNameWithGenerics(jpa);
		} else {
			if(hasIDClassAnnotation(inverseJPT)){
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
	private void addAppropriateDerivedIdAnnotation(JavaPersistentType ownerJPT,
			JavaPersistentType inverseJPT, JavaPersistentAttribute ownerAttr,
			String inverseIdClassFQN) {
		if(hasIDClassAnnotation(ownerJPT)){
			String ownerIdClassFQN = getIdType(ownerJPT);
			addDerivedIdAnnotation(ownerJPT, inverseJPT, ownerAttr, ownerIdClassFQN,
					inverseIdClassFQN, IdAnnotation.ANNOTATION_NAME);
		} else if(hasEmbeddedPk(ownerJPT)){
			String ownerIdClassFQN = JPAEditorUtil.getAttributeTypeNameWithGenerics(getEmbeddedIdAttribute(ownerJPT));
			addDerivedIdAnnotation(ownerJPT, inverseJPT, ownerAttr, ownerIdClassFQN,
					inverseIdClassFQN, MapsId2_0Annotation.ANNOTATION_NAME);
		} else if(hasSimplePk(ownerJPT)){
			ownerAttr.getResourceAttribute().addAnnotation(MapsId2_0Annotation.ANNOTATION_NAME);
		} else {
			ownerAttr.getResourceAttribute().addAnnotation(IdAnnotation.ANNOTATION_NAME);
		}
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
	 */
	private void addDerivedIdAnnotation(JavaPersistentType ownerJPT,
			JavaPersistentType inverseJPT, JavaPersistentAttribute ownerAttr,
			String ownerIdClassFQN,	String inverseIdClassFQN, String annotationName) {
		if(!inverseIdClassFQN.equals(ownerIdClassFQN)){
			String attributeType = JPAEditorUtil.returnSimpleName(inverseIdClassFQN);
			addFieldInCompositeKeyClass(inverseJPT, ownerAttr, ownerIdClassFQN, attributeType);
			Annotation ann = ownerAttr.getResourceAttribute().addAnnotation(annotationName);
			if(ann instanceof MapsId2_0Annotation){
				((MapsId2_0Annotation)ann).setValue(ownerAttr.getName());
			}
		} else {
			ownerAttr.getResourceAttribute().addAnnotation(annotationName);
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
	private void addFieldInCompositeKeyClass(JavaPersistentType inverseJPT,
			JavaPersistentAttribute ownerAttr, String fqnClass, String attributeTypeName) {
		IJavaProject javaProject = JavaCore.create(ownerAttr.getJpaProject().getProject());
		IType type = getType(javaProject, fqnClass);
		if(type != null && !type.getField(ownerAttr.getName()).exists()){
			ICompilationUnit unit = type.getCompilationUnit();
			JavaPersistentType jpt = JPAEditorUtil.getJPType(unit);
			Command createNewAttributeCommand = new AddAttributeCommand(null, jpt, attributeTypeName, null, ownerAttr.getName(),
					ownerAttr.getName(), null, null, false, unit);
			try {
				getJpaProjectManager().execute(createNewAttributeCommand, SynchronousUiCommandExecutor.instance());
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Cannot create a new attribute with name " + ownerAttr.getName(), e); //$NON-NLS-1$		
			}
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
