/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.ui.refactoring.RenameSupport;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayListIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnsAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestableAttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableAnnotation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.UpdateAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.BidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelDir;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToOneBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToOneUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.UnidirectionalRelation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;


@SuppressWarnings("restriction")
public class JpaArtifactFactory {

	private static final JpaArtifactFactory INSTANCE = new JpaArtifactFactory();	
	
	private static final int MAX_NUM_OF_ITERATIONS = 25;
	private static final int PAUSE_DURATION = 200;
	
	private static final String COLLECTION_TYPE = "java.util.Collection"; //$NON-NLS-1$
	private static final String LIST_TYPE = "java.util.List"; //$NON-NLS-1$
	private static final String SET_TYPE = "java.util.Set"; //$NON-NLS-1$
	private static final String MAP_TYPE = "java.util.Map"; //$NON-NLS-1$

	synchronized public static JpaArtifactFactory instance() {
		return INSTANCE;
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
	
	public void addOneToOneRelation(IFeatureProvider fp, JavaPersistentType ownerJPT, 
									JavaPersistentAttribute ownerAttibute, 
									JavaPersistentType referencedJPT, 
			JavaPersistentAttribute referencedAttribute, int direction) {
		
		if(ownerJPT.getAttributeNamed(ownerAttibute.getName()) == null){
			   refreshEntityModel(fp, ownerJPT);
		}
		
		JavaPersistentAttribute attr = (JavaPersistentAttribute) ownerJPT
				.resolveAttribute(ownerAttibute.getName());
		attr.getResourcePersistentAttribute().setPrimaryAnnotation("javax.persistence.OneToOne", new ArrayListIterable<String>());	//$NON-NLS-1$
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {
			JpaArtifactFactory.instance().refreshEntityModel(null, referencedJPT);
			JavaPersistentAttribute attr2 = (JavaPersistentAttribute) referencedJPT.resolveAttribute(referencedAttribute.getName());
			attr2.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			attr2.getJpaProject().getRootContextNode().update();
			JavaOneToOneMapping mapping = (JavaOneToOneMapping) attr2.getMapping();
			OneToOneAnnotation annotation = mapping.getMappingAnnotation();
			if(annotation == null) {
				JpaArtifactFactory.instance().refreshEntityModel(null, referencedJPT);
				annotation = ((JavaOneToOneMapping) attr2.getMapping()).getMappingAnnotation();
			}
			annotation.setMappedBy(ownerAttibute.getName());
		}		
		
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
	
	
	public void addOneToManyRelation(IFeatureProvider fp, JavaPersistentType singleSideJPT, 
									 JavaPersistentAttribute singleSideAttibute, 
									 JavaPersistentType manySideJPT, 
			JavaPersistentAttribute manySideAttribute, int direction, boolean isMap) {
		
		//if(singleSideJPT.getAttributeNamed(singleSideAttibute.getName()) == null){
			   refreshEntityModel(fp, singleSideJPT);
		//}
		
		JavaPersistentAttribute resolvedSingleSideAttribute = (JavaPersistentAttribute) singleSideJPT
				.resolveAttribute(singleSideAttibute.getName());
		resolvedSingleSideAttribute
				.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {
			resolvedSingleSideAttribute.getJpaProject().getRootContextNode()
					.update();
			JpaArtifactFactory.instance().refreshEntityModel(null, singleSideJPT);
			JavaAttributeMapping mapping = resolvedSingleSideAttribute
					.getMapping();
			if (!mapping.getClass().isInstance(JavaOneToManyMapping.class)) 
				return;
			OneToManyAnnotation annotation = ((JavaOneToManyMapping)mapping).getMappingAnnotation();
			if (annotation == null) {
				JpaArtifactFactory.instance().refreshEntityModel(null, singleSideJPT);
				mapping = resolvedSingleSideAttribute.getMapping();
				annotation = ((JavaOneToManyMapping)mapping).getMappingAnnotation();
			}
			
			if(annotation == null){
				JpaArtifactFactory.instance().refreshEntityModel(null, singleSideJPT);
				annotation = ((JavaOneToManyMapping)resolvedSingleSideAttribute
					.getMapping()).getMappingAnnotation();
			}
			annotation.setMappedBy(manySideAttribute.getName());						
			
		}
		if (isMap)
			singleSideAttibute.getResourcePersistentAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {
			if(manySideJPT.getAttributeNamed(manySideAttribute.getName()) == null){
				   refreshEntityModel(fp, manySideJPT);
			}
			
			JavaPersistentAttribute resolvedManySideAttribute = (JavaPersistentAttribute) manySideJPT
					.resolveAttribute(manySideAttribute.getName());
			resolvedManySideAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		} else {
			addJoinColumnIfNecessary(resolvedSingleSideAttribute, singleSideJPT, fp);
		}		
	}
	
	private void addJoinColumnIfNecessary(JavaPersistentAttribute jpa,
			JavaPersistentType jpt, IFeatureProvider fp) {

		if (JPAEditorUtil.checkJPAFacetVersion(jpa.getJpaProject(), "1.0") || //$NON-NLS-1$
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
						.getResourcePersistentAttribute().addAnnotation(
								JoinColumnAnnotation.ANNOTATION_NAME);
				String idColName = getColumnName(ids[0]);
				an.setName(tableName + "_" + idColName); //$NON-NLS-1$
				an.setReferencedColumnName(idColName);
			} else {
				Hashtable<String, String> atNameToColName = getOverriddenColNames(ids[0]);
				JoinColumnsAnnotation an = (JoinColumnsAnnotation) jpa
						.getResourcePersistentAttribute().addAnnotation(
								JoinColumnsAnnotation.ANNOTATION_NAME);
				PersistenceUnit pu = getPersistenceUnit(jpt);
				String embeddableTypeName = ids[0].getTypeName();
				Embeddable emb = pu.getEmbeddable(embeddableTypeName);
				Iterator<AttributeMapping> amIt = emb.allAttributeMappings();
				while (amIt.hasNext()) {
					AttributeMapping am = amIt.next();
					NestableJoinColumnAnnotation jc = an.addNestedAnnotation();
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
			JoinColumnsAnnotation an = (JoinColumnsAnnotation) jpa
					.getResourcePersistentAttribute().addAnnotation(
							JoinColumnsAnnotation.ANNOTATION_NAME);
			for (JavaPersistentAttribute idAt : ids) {
				NestableJoinColumnAnnotation jc = an.addNestedAnnotation();
				String idColName = getColumnName(idAt);
				jc.setName(tableName + "_" + idColName); //$NON-NLS-1$
				jc.setReferencedColumnName(idColName);
			}
		}
	}

	private Hashtable<String, String> getOverriddenColNames(
			JavaPersistentAttribute embIdAt) {
		Hashtable<String, String> res = new Hashtable<String, String>();
		AttributeOverrideAnnotation aon = (AttributeOverrideAnnotation) embIdAt
				.getResourcePersistentAttribute().getAnnotation(
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
		AttributeOverridesAnnotation aosn = (AttributeOverridesAnnotation) embIdAt
				.getResourcePersistentAttribute().getAnnotation(
						AttributeOverridesAnnotation.ANNOTATION_NAME);
		if (aosn == null)
			return res;
		Iterable<NestableAttributeOverrideAnnotation> it = aosn
				.getNestedAnnotations();
		if (it == null)
			return res;
		Iterator<NestableAttributeOverrideAnnotation> iter = it.iterator();
		while (iter.hasNext()) {
			NestableAttributeOverrideAnnotation an = iter.next();
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
	
	public void addManyToOneRelation(IFeatureProvider fp, JavaPersistentType manySideJPT, 
									 JavaPersistentAttribute manySideAttribute, 
									 JavaPersistentType singleSideJPT, 
									 JavaPersistentAttribute singleSideAttibute, 
									 int direction, boolean isMap) {
		
		refreshEntityModel(fp, manySideJPT);
		
		JavaPersistentAttribute resolvedManySideAttribute = manySideJPT.getAttributeNamed(manySideAttribute.getName());
		resolvedManySideAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		if (direction == JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL) 
			return;
		
		JavaPersistentAttribute resolvedSingleSideAttribute = singleSideJPT.getAttributeNamed(singleSideAttibute.getName());
		resolvedSingleSideAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		refreshEntityModel(fp, singleSideJPT);
		JavaOneToManyMapping mapping = (JavaOneToManyMapping)resolvedSingleSideAttribute.getMapping();
		OneToManyAnnotation a = mapping.getMappingAnnotation();
		if (a == null) 
			return;
		a.setMappedBy(manySideAttribute.getName());
		if (isMap)
			singleSideAttibute.getResourcePersistentAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
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
		
		if(ownerSideJPT.getAttributeNamed(ownerSideAttribute.getName()) == null){
			   refreshEntityModel(fp, ownerSideJPT);
		}
		
		JavaPersistentAttribute resolvedOwnerSideAttribute = (JavaPersistentAttribute) ownerSideJPT
				.resolveAttribute(ownerSideAttribute.getName());
		resolvedOwnerSideAttribute
				.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		if (isMap)
			resolvedOwnerSideAttribute.getResourcePersistentAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		
		if (direction == JPAEditorConstants.RELATION_TYPE_BIDIRECTIONAL) {
			JpaArtifactFactory.instance().refreshEntityModel(null, inverseSideJPT);
			/*
			if(inverseSideJPT.getAttributeNamed(inverseSideAttibute.getName()) == null){
				   refreshEntityModel(fp, inverseSideJPT);
			}
			*/
			
			JavaPersistentAttribute resolvedInverseSideAttribute = (JavaPersistentAttribute) inverseSideJPT
					.resolveAttribute(inverseSideAttibute.getName());
			resolvedInverseSideAttribute
					.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			resolvedInverseSideAttribute.getJpaProject().getRootContextNode()
					.update();
			
			JavaManyToManyMapping mapping = (JavaManyToManyMapping) resolvedInverseSideAttribute
					.getMapping();
			ManyToManyAnnotation a = mapping.getMappingAnnotation();
			if(mapping == null || a == null){
				JpaArtifactFactory.instance().refreshEntityModel(null, inverseSideJPT);
				mapping = (JavaManyToManyMapping) resolvedInverseSideAttribute
					.getMapping();
				a = mapping.getMappingAnnotation();
			}
			if (a == null)
				return;
			a.setMappedBy(ownerSideAttribute.getName());	
			if (isMap)
				resolvedInverseSideAttribute.getResourcePersistentAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		}		
		
	}
		
	public void restoreEntityClass(JavaPersistentType jpt,
			IJPAEditorFeatureProvider fp) {
		fp.restoreEntity(jpt);
	}    	
	
	public void forceSaveEntityClass(final JavaPersistentType jpt,
			IJPAEditorFeatureProvider fp) {
		final ICompilationUnit cu = fp.getCompilationUnit(jpt);
		Display.getDefault().asyncExec(new Runnable() {
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
			JpaProject jpaProject = jpt.getJpaProject();
			jpaProject.updateAndWait();
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
		Iterator<ClassRef> it = unit.classRefs();
		while (it.hasNext()) {
			ClassRef ref = it.next();
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
		JavaResourcePersistentType jrpt = convertJPTToJRPT(jpt);
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
		JavaResourcePersistentType jrpt = convertJPTToJRPT(jpt);
		if (jrpt == null)
			return false;
		JavaEntity mapping = (JavaEntity) jpt.getMapping();
		if (mapping != null)
			return (mapping.getSpecifiedName() != null);
		return false;
							}
	
	/*
	@SuppressWarnings("unchecked")
	public String getAttributeName(JavaPersistentAttribute jpa) {
		
		JavaResourcePersistentType jrpt = jpa.getJpaProject()
				.getJavaResourcePersistentType(jpa.getName());
		ASTNode nd = jrpt.getMember().getModifiedDeclaration().getDeclaration();
		if (nd instanceof TypeDeclaration) {
			ListIterator<?> modfs = ((TypeDeclaration) nd).modifiers()
					.listIterator();
			while (modfs.hasNext()) {
				Object modf = modfs.next();
				if (modf instanceof NormalAnnotation) {
					NormalAnnotation an = (NormalAnnotation)modf;
						List<MemberValuePair> vals = an.values();
						if (vals != null) {
							for (int i = 0; i < vals.size(); i++) {
								MemberValuePair mvp = vals.get(i);
								if (mvp.getName().toString().equals("name")) ; //$NON-NLS-1$
							return JPAEditorUtil.stripQuotes(mvp.getValue()
									.toString());
								
							}
						}
				} 			
			}
		}		
		return jpa.getName();
	}
	*/
	
	public boolean hasEntityAnnotation(JavaPersistentType jpt) {
		return (jpt.getMapping() instanceof JavaEntity);
	}	
	
	public String getSpecifiedEntityName(JavaPersistentType jpt){
		JavaEntity gje = (JavaEntity) jpt.getMapping();
		return gje.getSpecifiedName();
	}
	
	public void renameEntity(JavaPersistentType jpt, String newName) {
		JavaEntity gje = (JavaEntity)jpt.getMapping();
		gje.setSpecifiedName(newName);	
	}
	
	public JavaPersistentAttribute addAttribute(IJPAEditorFeatureProvider fp, JavaPersistentType jpt, 
			JavaPersistentType attributeType,  String attributeName,
			String actName, boolean isCollection, ICompilationUnit cu1,
			ICompilationUnit cu2) {
		
		return addAttribute(fp, jpt, attributeType, null, attributeName,
				actName, isCollection, cu1, cu2);
	}
	
	public JavaPersistentAttribute addAttribute(IJPAEditorFeatureProvider fp, JavaPersistentType jpt, 
												JavaPersistentType attributeType, String mapKeyType, String attributeName,
												String actName, boolean isCollection, ICompilationUnit cu1,
												ICompilationUnit cu2) {
		IType type = null;
		try {
			JPAEditorUtil.createImport(cu1, cu2.getType(attributeType.getName()).getElementName());
			type = cu1.findPrimaryType();	
			refreshEntityModel(fp, jpt);
			if (doesAttributeExist(jpt, actName)) {
				return (JavaPersistentAttribute) jpt
						.resolveAttribute(attributeName);
			}
			if (isCollection) {
				IProject project = jpt.getJpaProject().getProject();
				Properties props = fp.loadProperties(project);

				if (JPADiagramPropertyPage.isCollectionType(project, props)) {
					createContentType(attributeType, actName, cu1, type, COLLECTION_TYPE);
					type.createMethod(genGetterWithAppropriateType(attributeName,
							JPAEditorUtil.returnSimpleName(attributeType.getName()), 
							actName, COLLECTION_TYPE), null, false,
							new NullProgressMonitor());
					type.createMethod(genSetterWithAppropriateType(attributeName,
							JPAEditorUtil.returnSimpleName(attributeType.getName()), 
							actName, COLLECTION_TYPE), null, false,
							new NullProgressMonitor());
				} else if (JPADiagramPropertyPage.isListType(project, props)) {
					createContentType(attributeType, actName, cu1, type, LIST_TYPE);
					type.createMethod(genGetterWithAppropriateType(attributeName,
							JPAEditorUtil.returnSimpleName(attributeType.getName()), 
							actName, LIST_TYPE), null, false,
							new NullProgressMonitor());
					type.createMethod(genSetterWithAppropriateType(attributeName,
							JPAEditorUtil.returnSimpleName(attributeType.getName()), 
							actName, LIST_TYPE), null, false,
							new NullProgressMonitor());
				} else if (JPADiagramPropertyPage.isSetType(project, props)) {
					createContentType(attributeType, actName, cu1, type, SET_TYPE);
					type.createMethod(genGetterWithAppropriateType(attributeName,
							JPAEditorUtil.returnSimpleName(attributeType.getName()), 
							actName, SET_TYPE), null, false,
							new NullProgressMonitor());
					type.createMethod(genSetterWithAppropriateType(attributeName,
							JPAEditorUtil.returnSimpleName(attributeType.getName()), 
							actName, SET_TYPE), null, false,
							new NullProgressMonitor());
				} else {
					mapKeyType = createContentType(mapKeyType, attributeType, actName, cu1, type, MAP_TYPE);
					type.createMethod(genGetterWithAppropriateType(attributeName, mapKeyType,
							JPAEditorUtil.returnSimpleName(attributeType.getName()), 
							actName, MAP_TYPE), null, false,
							new NullProgressMonitor());
					type.createMethod(genSetterWithAppropriateType(attributeName, mapKeyType,
							JPAEditorUtil.returnSimpleName(attributeType.getName()), 
							actName, MAP_TYPE), null, false,
							new NullProgressMonitor());
				}
			} else {
				type
						.createField(
								"  private " + JPAEditorUtil.returnSimpleName(attributeType.getName()) + " " + JPAEditorUtil.decapitalizeFirstLetter(actName) + ";", null, false, new NullProgressMonitor()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				type.createMethod(genGetterContents(attributeName,
						JPAEditorUtil.returnSimpleName(attributeType.getName()), null,
						actName, null, isCollection), null, false,
						new NullProgressMonitor());
				type.createMethod(genSetterContents(attributeName,
						JPAEditorUtil.returnSimpleName(attributeType.getName()), null,
						actName, isCollection), null, false,
						new NullProgressMonitor());
			}
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannnot create a new attribute with name " + attributeName, e); //$NON-NLS-1$				
		}
		
		if(jpt.getAttributeNamed(attributeName) == null){
			   refreshEntityModel(fp, jpt);
		}
		
		JavaPersistentAttribute res =  getAttributeFromEntity(jpt, actName);
		return res;
	}

	private void createContentType(JavaPersistentType attributeType,
			String actName, ICompilationUnit cu1, IType type, String collectionType)
			throws JavaModelException {
		createContentType(null, attributeType,
				actName, cu1, type, collectionType);
	}
	
	private String createContentType(String mapKeyType, JavaPersistentType attributeType,
			String actName, ICompilationUnit cu1, IType type, String collectionType)
			throws JavaModelException {
		
		if (mapKeyType != null) {
			mapKeyType = JPAEditorUtil.createImport(cu1, mapKeyType); 
		}
		JPAEditorUtil.createImport(cu1, collectionType);
		type.createField(
				"  private " + JPAEditorUtil.returnSimpleName(collectionType) + "<" +//$NON-NLS-1$ //$NON-NLS-2$
				((mapKeyType != null) ? (mapKeyType + ", ") : "") +			//$NON-NLS-1$ //$NON-NLS-2$
				JPAEditorUtil.returnSimpleName(attributeType.getName()) + "> " + JPAEditorUtil.decapitalizeFirstLetter(actName) +  //$NON-NLS-1$
				";", null, false, new NullProgressMonitor()); //$NON-NLS-1$ 
		return mapKeyType;
	}
	
	public void refreshEntityModel(IFeatureProvider fp, JavaPersistentType jpt) {
		if(convertJPTToJRPT(jpt) == null)
			return;
		if (fp == null) {
			jpt.update();
			return;
		}
		Shape el = (Shape) fp.getPictogramElementForBusinessObject(jpt);
		if(JPACheckSum.INSTANCE().isEntityModelChanged(el, jpt.getJpaProject())){
			try {
				jpt.update();
			} catch (ArrayIndexOutOfBoundsException e) {
				
			}
		}
	}
	
	public boolean isCollection(ContainerShape entityShape,
			IJPAEditorFeatureProvider fp, String attrTxt) {
		Object ob = fp.getBusinessObjectForPictogramElement(entityShape);
		if(ob instanceof JavaPersistentType){
			JavaPersistentType jpt = (JavaPersistentType) ob;
			ICompilationUnit cu = fp.getCompilationUnit(jpt);
				IType type = cu.getType(JPAEditorUtil.returnSimpleName(jpt.getName()));
			IField field = type.getField(attrTxt);
			int cnt = 0;
			while ((cnt < 20) && !field.exists()) {
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					JPADiagramEditorPlugin.logError("Sleep interrupted", e); //$NON-NLS-1$				
				}
				field = type.getField(attrTxt);
				cnt++;
			}
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
	
	public String createNewAttribute(JavaPersistentType jpt, 
			boolean isCollection, IJPAEditorFeatureProvider fp) {
		
		ICompilationUnit ijl = fp.getCompilationUnit(jpt);
		String attrTypeName = "java.lang.String"; 																	//$NON-NLS-1$
		String newAttrName = genUniqueAttrName(jpt, attrTypeName, fp);
		return addNewAttribute(jpt, ijl, newAttrName, attrTypeName,
				"@Basic", newAttrName, isCollection, fp); //$NON-NLS-1$
	}
	
	/*
	 * public String createNewAttribute(JavaPersistentType jpt, String attrName,
	 * String attrTypeName, String annotation, String actName, boolean
	 * isCollection, JPAEditorFeatureProvider fp) {
	 * 
	 * ICompilationUnit ijl = fp.getCompilationUnit(jpt); return
	 * addNewAttribute(jpt, ijl, attrName, attrTypeName, annotation, actName,
	 * isCollection, fp); }
	*/
	
	public JavaPersistentAttribute createANewAttribute(JavaPersistentType jpt, 
			String attrName, String attrTypeName, String[] attrTypeElementNames,
			String actName, List<String> annotations, boolean isCollection,
			boolean isMethodAnnotated, IJPAEditorFeatureProvider fp) {
		
		ICompilationUnit ijl = fp.getCompilationUnit(jpt);
		return addANewAttribute(jpt, ijl, attrName, attrTypeName,
				attrTypeElementNames, actName, annotations, isCollection,
				isMethodAnnotated, fp);
	}
		
	private JavaPersistentAttribute addANewAttribute(JavaPersistentType jpt, 
			ICompilationUnit cu, String attrName, String attrTypeName,
			String[] attrTypeElementNames, String actName,
			List<String> annotations, boolean isCollection,
			boolean isMethodAnnotated, IJPAEditorFeatureProvider fp) {
		
		JavaPersistentAttribute attr = null;
		try {
			attr = makeNewAttribute(fp, jpt, cu, attrName, attrTypeName,
					attrTypeElementNames, actName, annotations, isCollection,
					isMethodAnnotated);
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannot create a new attribute with name " + attrName, e); //$NON-NLS-1$		
		}
		return attr;						
	}
	
	public String addNewAttribute(JavaPersistentType jpt, ICompilationUnit cu,
			String attrName, String attrTypeName, String annotation,
			String actName, boolean isCollection, IJPAEditorFeatureProvider fp) {
		
		try {
			List<String> annotations = new LinkedList<String>();
			annotations.add(annotation);
			boolean isMethodAnnotated = JpaArtifactFactory.instance()
					.isMethodAnnotated(jpt);
			makeNewAttribute(fp, jpt, cu, attrName, attrTypeName, null, actName,
					annotations, isCollection, isMethodAnnotated);
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannot create a new attribute with name " + attrName, e); //$NON-NLS-1$		
		}
		return attrName;				
	}
	
	public JavaPersistentAttribute makeNewAttribute(IFeatureProvider fp, JavaPersistentType jpt, 
			ICompilationUnit cu, String attrName, String attrTypeName,
			String[] attrTypes, String actName,
			List<String> annotations, boolean isCollection,
			boolean isMethodAnnotated) throws JavaModelException {
		
		IType type = cu.findPrimaryType();
		String contents = ""; 														//$NON-NLS-1$
		isMethodAnnotated = (annotations != null) && (!annotations.isEmpty()) ? isMethodAnnotated
				: JpaArtifactFactory.INSTANCE.isMethodAnnotated(jpt);
		
		if (!isMethodAnnotated) {
			if (annotations != null) {
				Iterator<String> it = annotations.iterator();
				while (it.hasNext()) {
					String an = it.next();
					contents += "   " + an + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
				}							
			}
		}
		
		if(annotations!=null && annotations.contains("@Basic")){ //$NON-NLS-1$
			if(!cu.getImport("javax.persistence.*").exists() && !cu.getImport("javax.persistence.Basic").exists()){ //$NON-NLS-1$ //$NON-NLS-2$
				JPAEditorUtil.createImports(cu, "javax.persistence.Basic"); //$NON-NLS-1$
			}
		}
		
		boolean shouldAddImport = true;
		IImportDeclaration[] importDeclarations = cu.getImports();
		String attrShortTypeName = JPAEditorUtil.returnSimpleName(attrTypeName);
		for(IImportDeclaration importDecl : importDeclarations){
			String importedDeclarationFQN = importDecl.getElementName();
			String importedDeclarationShortName = JPAEditorUtil.returnSimpleName(importedDeclarationFQN);
			if(attrShortTypeName.equals(importedDeclarationShortName) && !attrTypeName.equals(importedDeclarationFQN))
				shouldAddImport = false;
		}
		
		if(shouldAddImport){
			JPAEditorUtil.createImports(cu, attrTypeName);
		    attrTypeName = JPAEditorUtil.returnSimpleName(attrTypeName);
		}
		if ((attrTypes != null) && (attrTypes.length > 0)) {
			JPAEditorUtil.createImports(cu, attrTypes);
		}
		
		contents += "    private " + attrTypeName + //$NON-NLS-1$
				((attrTypes == null) ? "" : ("<" + JPAEditorUtil.createCommaSeparatedListOfSimpleTypeNames(attrTypes) + ">")) + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				" " + attrName + ";"; //$NON-NLS-1$ //$NON-NLS-2$		

		type.createMethod(
				genSetterContents(attrName, attrTypeName, attrTypes,
						actName, isCollection), null, false,
				new NullProgressMonitor());
		if (isMethodAnnotated) {
			type.createMethod(
					genGetterContents(attrName, attrTypeName,
							attrTypes, actName, annotations,
							isCollection), null, false,
					new NullProgressMonitor());
			type.createField(contents, null, false, new NullProgressMonitor());
		} else {
			type.createField(contents, null, false, new NullProgressMonitor());
			type.createMethod(
					genGetterContents(attrName, attrTypeName,
							attrTypes, actName, null, isCollection),
					null, false, new NullProgressMonitor());
		}		
		
		int cnt = 0;
		refreshEntityModel(fp, jpt);
		JavaPersistentAttribute jpa = jpt.getAttributeNamed(attrName);
		while ((jpa == null) && (cnt < 25)) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError(e);
			}
			jpa = jpt.getAttributeNamed(attrName);
			cnt++;
		}
			
		/*
		if(jpt.getAttributeNamed(attrName) == null){
			   refreshEntityModel(fp, jpt);
		}
		*/
		
		return getAttributeFromEntity(jpt, attrName);
	}
		
	public void deleteAttribute(JavaPersistentType jpt, String attributeName,
								IJPAEditorFeatureProvider fp) {
		
		String attrNameWithCapitalLetter = attributeName.substring(0, 1)
				.toUpperCase(Locale.ENGLISH)
				+ attributeName.substring(1);
		ICompilationUnit compUnit = fp.getCompilationUnit(jpt);		
		IType javaType = compUnit.findPrimaryType();
		String typeSignature = null;
		String getterPrefix = "get"; 			//$NON-NLS-1$
		String methodName = getterPrefix + attrNameWithCapitalLetter; 
		IMethod getAttributeMethod = javaType.getMethod(methodName,
				new String[0]);
		if (!getAttributeMethod.exists()) {
			JavaPersistentAttribute jpa = jpt.getAttributeNamed(attributeName);
			String typeName = jpa.getResourcePersistentAttribute().getTypeName();
			if ("boolean".equals(typeName)) {										//$NON-NLS-1$
				getterPrefix = "is";												//$NON-NLS-1$
				methodName = getterPrefix + attrNameWithCapitalLetter; 				
				getAttributeMethod = javaType.getMethod(methodName,
						new String[0]);
			}		
			try {
				if ((getAttributeMethod != null) && getAttributeMethod.exists());
					typeSignature = getAttributeMethod.getReturnType();
			} catch (JavaModelException e1) {
				JPADiagramEditorPlugin.logError("Cannot obtain the type of the getter with name " + methodName + "()", e1); 	//$NON-NLS-1$	//$NON-NLS-2$
			}			
		}
		if (typeSignature == null)
		 	methodName = null;		
		
		boolean isMethodAnnotated = JpaArtifactFactory.instance()
				.isMethodAnnotated(jpt);
		if (isMethodAnnotated) {
			try {
				IField attributeField = javaType.getField(attributeName);
				
				if ((attributeField != null) && !attributeField.exists())
					attributeField = javaType.getField(JPAEditorUtil.revertFirstLetterCase(attributeName));
				if ((attributeField != null) && attributeField.exists()) 
					attributeField.delete(true, new NullProgressMonitor());
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot remove the attribute field with name " + attributeName, e); 	//$NON-NLS-1$	
			} 
			try {
				methodName = getterPrefix + attrNameWithCapitalLetter; //$NON-NLS-1$
				if (getAttributeMethod != null) {
					typeSignature = getAttributeMethod.getReturnType();
					if (getAttributeMethod.exists())
						getAttributeMethod.delete(true, new NullProgressMonitor());
				}
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot remove the attribute getter with name " + methodName + "()", e); 	//$NON-NLS-1$	 //$NON-NLS-2$
			} 	
		} else {
			try {
				methodName = getterPrefix + attrNameWithCapitalLetter; //$NON-NLS-1$
				if (getAttributeMethod.exists()) {
					typeSignature = getAttributeMethod.getReturnType();
					getAttributeMethod.delete(true, new NullProgressMonitor());
				}
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot remove the attribute getter with name " + methodName + "()", e); 	//$NON-NLS-1$	 //$NON-NLS-2$
			} 	
			try {
				IField attributeField = javaType.getField(attributeName);
				if (attributeField != null)
					if (!attributeField.exists())
						attributeField = javaType.getField(JPAEditorUtil.revertFirstLetterCase(attributeName));			
				if ((attributeField != null) && attributeField.exists())
					attributeField.delete(true, new NullProgressMonitor());
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot remove the attribute field with name " + attributeName, e); 	//$NON-NLS-1$	
			} 			
		}
		try {
			methodName = "set" + attrNameWithCapitalLetter; //$NON-NLS-1$
			IMethod setAttributeMethod = javaType.getMethod(methodName,
					new String[] { typeSignature });
			if ((setAttributeMethod != null) && setAttributeMethod.exists())
				setAttributeMethod.delete(true, new NullProgressMonitor());
		} catch (Exception e) {
			JPADiagramEditorPlugin.logError("Cannot remove the attribute setter with name " + methodName + "(...)", e); //$NON-NLS-1$ //$NON-NLS-2$	
		} 		
		
		refreshEntityModel(fp, jpt);
		
		ReadOnlyPersistentAttribute at = jpt.resolveAttribute(attributeName);
		int c = 0;
		while ((at != null) && (c < MAX_NUM_OF_ITERATIONS)) { 
			try {
				Thread.sleep(PAUSE_DURATION);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Thread.sleep() interrupted", e); //$NON-NLS-1$		
			}	
			at = jpt.getAttributeNamed(attributeName);			
			c++;
		}
	}

	private String genUniqueAttrName(JavaPersistentType jpt, 
			String attrTypeName, IJPAEditorFeatureProvider fp) {
		
		ICompilationUnit ijl = fp.getCompilationUnit(jpt);
		IType type = null;
		type = ijl.findPrimaryType();
		Iterator<String> it = jpt.attributeNames();
		Set<String> attrNames = new HashSet<String>();
		while (it.hasNext()) {
			attrNames.add(it.next());
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
		
	public Annotation[] getAnnotations(JavaPersistentAttribute persistentAttribite) {	
		JavaResourcePersistentAttribute jrpt = persistentAttribite.getResourcePersistentAttribute();
		Annotation[] res = new Annotation[jrpt.annotationsSize()];
		                                  //mappingAnnotationsSize() + jrpt.supportingAnnotationsSize()];
		Iterator<Annotation> it = jrpt.annotations();
		int c = 0;
		while (it.hasNext()) {
			res[c] = it.next();
			c++;
		}
		/*
		it = jrpt.supportingAnnotations();
		while (it.hasNext()) {
			res[c] = it.next();
			c++;
		}
		*/
		return res;
	}		
	
	public HashSet<String> getAnnotationNames(
			JavaPersistentAttribute persistentAttribite) {
		
		JavaResourcePersistentAttribute jrpt = persistentAttribite.getResourcePersistentAttribute();
		HashSet<String> res = new HashSet<String>();
		Iterator<Annotation> it = jrpt.annotations();
		while (it.hasNext())
			res.add(JPAEditorUtil.returnSimpleName(it.next().getAnnotationName()));
		/*
		it = jrpt.supportingAnnotations();
		while (it.hasNext()) 
			res.add(JPAEditorUtil.cutFromLastDot(it.next().getAnnotationName()));
		*/
		return res;
	}	
	
	public List<String> getAnnotationStrings(
			JavaPersistentAttribute persistentAttribite) {
		
		JavaPersistentType jpt = (JavaPersistentType)persistentAttribite.getParent();
		CompilationUnit jdtCU = jpt.getResourcePersistentType().getJavaResourceCompilationUnit().buildASTRoot();
		JavaResourcePersistentAttribute jrpt = persistentAttribite.getResourcePersistentAttribute();
		List<String> res = new LinkedList<String>();
		Iterator<Annotation> it = jrpt.annotations();
		while (it.hasNext()) {
			Annotation an = it.next();
			org.eclipse.jdt.core.dom.Annotation jdtAn = an.getAstAnnotation(jdtCU);
			res.add(jdtAn.toString());
		}
		/*
		it = jrpt.supportingAnnotations();
		while (it.hasNext()) { 
			Annotation an = it.next();
			org.eclipse.jdt.core.dom.Annotation jdtAn = an.getJdtAnnotation(jdtCU);
			res.add(jdtAn.toString());
		}
		*/
		return res;
	}	
		
	private boolean isNonOwner(JavaPersistentAttribute at) {
		JavaAttributeMapping jam = at.getMapping();
		//if (jam.getMappingAnnotation() == null) {
			JpaArtifactFactory.instance().refreshEntityModel(null, (JavaPersistentType)at.getParent());
			jam = at.getMapping();
		//}
		if (!(jam.getMappingAnnotation() instanceof OwnableRelationshipMappingAnnotation))
			return false;
		OwnableRelationshipMappingAnnotation nom = (OwnableRelationshipMappingAnnotation)jam.getMappingAnnotation();
		return nom.getMappedBy() != null;
	}
	
	public Collection<IRelation> produceAllRelations(
			JavaPersistentType newJPT, IJPAEditorFeatureProvider fp) {
		
		Collection<IRelation> res = produceRelations(newJPT, fp);
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
				Collection<IRelation> rels = produceRelations(jpt, newJPT, fp);
				res.addAll(rels);
			//}
		}
		return res;
	 }
	
	private Collection<IRelation> produceRelations(
			JavaPersistentType newJPT, IJPAEditorFeatureProvider fp) {
		
		ListIterator<JavaPersistentAttribute> it = newJPT.attributes();
		HashSet<IRelation> res = new HashSet<IRelation>();
		while (it.hasNext()) {
			JavaPersistentAttribute at = it.next();
			IRelation rel = produceRelation(at, fp);
			if (rel != null)
				res.add(rel);
		}
		return res;
	}
	
	public boolean isRelationAnnotated(JavaPersistentAttribute jpa) {
		
		HashSet<String> anNames = getAnnotationNames(jpa);
		Iterator<String> it = anNames.iterator();
		while (it.hasNext()) {
			String anName = it.next();
			if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(anName))
				return true;
		}
		return false;		
	}
	
	public IRelation produceRelation(
			JavaPersistentAttribute persistentAttribite,
			IJPAEditorFeatureProvider fp) {
		
		JavaResourcePersistentAttribute jrpa = persistentAttribite
				.getResourcePersistentAttribute();
		
		IRelation res = null;
		Annotation[] ans = getAnnotations(persistentAttribite);
		for (Annotation an : ans) {
			String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
			if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(annotationName)) {
				String relTypeName = getRelTypeName((RelationshipMappingAnnotation)an, jrpa);
				JavaPersistentType relJPT = (JavaPersistentType)fp.getBusinessObjectForKey(relTypeName);
				if (relJPT != null) 
					res = produceRelation(persistentAttribite, an, relJPT, fp);
				return res;
			}									
		}
		return res;
				
	}
		
	private Collection<IRelation> produceRelations(JavaPersistentType jpt1,
			JavaPersistentType jpt2, IJPAEditorFeatureProvider fp) {
		
		Collection<IRelation> resSet = new HashSet<IRelation>();
		ListIterator<JavaPersistentAttribute> it = jpt1.attributes();
		IRelation res = null;
		while (it.hasNext()) {
			JavaPersistentAttribute at = it.next();
			IResource r = at.getParent().getResource();
			if (!r.exists())
				throw new RuntimeException();
			try {
				JavaResourcePersistentAttribute jrpa = at.getResourcePersistentAttribute();
				Annotation[] ans = this.getAnnotations(at);
				for (Annotation an : ans) {
					String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
					if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(annotationName)) {
						String relTypeName = getRelTypeName((RelationshipMappingAnnotation)an, jrpa);
						if (!relTypeName.equals(jpt2.getName()))
							continue;
						JavaPersistentType relJPT = (JavaPersistentType) fp
								.getBusinessObjectForKey(jpt2.getName());
						res = produceRelation(at, an, relJPT, fp);
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
	 * Returns the relation annotation of the given attribute if there is any. If not - returns null
	 * 
	 * @param at
	 * @return
	 */
	
	/*
	private IAnnotation getRelationAnnotation(JavaPersistentAttribute at) {
		JavaResourcePersistentAttribute jrpa = at.getResourcePersistentAttribute();
		IAnnotatable m = getAttributeMember(at);
		IAnnotation[] ans = null;
		try {
			ans = m.getAnnotations();
		} catch (JavaModelException e) {
			tracer.error("", e);
			return null;
		}
		for (IAnnotation an : ans) {
			if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(JPAEditorUtil.cutFromLastDot(an.getElementName()))) {
				return an;
			}
		}
		return null;
	}
	*/
	
	/*
	public JavaPersistentAttribute getRelatedAttribute(JavaPersistentType jpt,
			JavaPersistentType relJPT) {

		ListIterator<JavaPersistentAttribute> it = relJPT.attributes();
		while (it.hasNext()) {
			JavaPersistentAttribute relEntityAt = it.next();
			JavaResourcePersistentAttribute jrpa = relEntityAt
					.getResourcePersistentAttribute();
			ASTNode nd = jrpa.getMember().getModifiedDeclaration()
					.getDeclaration();
			if ((nd instanceof MethodDeclaration)
					|| (nd instanceof FieldDeclaration)) {
				ListIterator<?> modfs = ((BodyDeclaration) nd).modifiers()
						.listIterator();
				while (modfs.hasNext()) {
					Object modf = modfs.next();
					if (modf instanceof Annotation) {
						Annotation an = (Annotation) modf;
						String annotationName = an.getTypeName()
								.getFullyQualifiedName();
						annotationName = annotationName
								.substring(annotationName.lastIndexOf('.') + 1);
						if (JPAEditorConstants.RELATION_ANNOTATIONS
								.contains(annotationName)) {
							String ownerTypeName = getRelTypeName(an, jrpa);
							if (!ownerTypeName.equals(jpt.getName()))
								continue;
							return relEntityAt;
						}
					}
				}
			}

		}
		return null;
	}
	*/
	
	
	/*
	 * If the given attribute is relation annotated and points to some entity,
	 * which is visualized in the diagram this method returns the pointed
	 * entity. Otherwise - null
	 */
	
	/*
	public JavaPersistentType getRelatedEntity(JavaPersistentAttribute jpa, 
											   IJPAEditorFeatureProvider fp) {
		
		JavaResourcePersistentAttribute jrpa = jpa
				.getResourcePersistentAttribute();
		IType t = jrpa.getJavaResourceCompilationUnit().getCompilationUnit().findPrimaryType();
		IAnnotatable m = getAttributeMember(jpa);
		IAnnotation[] ans = m.getAnnotations();
		for (IAnnotation an : ans) {
			String annotationName = an.getElementName();
			annotationName = JPAEditorUtil.cutFromLastDot(annotationName);

			if (JPAEditorConstants.RELATION_ANNOTATIONS
					.contains(annotationName)) {
				String relTypeName = getRelTypeName(an, jrpa);						
				JavaPersistentType relJPT = (JavaPersistentType) jpa
						.getPersistentType().getPersistenceUnit()
						.getPersistentType(relTypeName);
				if (relJPT != null)
					if (fp.getPictogramElementForBusinessObject(relJPT) != null)
						return relJPT;
			}						
			
			
		}
		
		if ((nd instanceof MethodDeclaration)
				|| (nd instanceof FieldDeclaration)) {
			ListIterator<?> modfs = ((BodyDeclaration) nd).modifiers()
					.listIterator();
			while (modfs.hasNext()) {
				Object modf = modfs.next();
				if (modf instanceof Annotation) {
					Annotation an = (Annotation)modf;
					String annotationName = an.getTypeName()
							.getFullyQualifiedName();
					annotationName = annotationName.substring(annotationName
							.lastIndexOf('.') + 1);
					if (JPAEditorConstants.RELATION_ANNOTATIONS
							.contains(annotationName)) {
						String relTypeName = getRelTypeName(an, jrpa);						
						JavaPersistentType relJPT = (JavaPersistentType) jpa
								.getPersistentType().getPersistenceUnit()
								.getPersistentType(relTypeName);
						if (relJPT != null)
							if (fp.getPictogramElementForBusinessObject(relJPT) != null)
								return relJPT;
					}						
				}														
			}
		}
		return null;
	}
	*/
		
	public JavaPersistentType getJPT(String name, PersistenceUnit pu) {
		pu.getJpaProject().updateAndWait();
		JavaPersistentType jpt = (JavaPersistentType) pu.getPersistentType(name);
		int cnt = 0;
		while ((jpt == null) && (cnt < MAX_NUM_OF_ITERATIONS)) {
			try {
				Thread.sleep(PAUSE_DURATION);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Sleep interrupted", e); //$NON-NLS-1$		
			}
			jpt = (JavaPersistentType)pu.getPersistentType(name);
			cnt++;
		}		
		return jpt;
	}
	
	/*
	 * Return all the attributes belonging to another entities and
	 * involved in a relation with the entity given as parameter
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
			if(!jrcu.persistentTypes().hasNext())
				continue;
			JavaResourcePersistentType jrpt = jrcu.persistentTypes().next();
			String name = jrpt.getQualifiedName();
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

	/*
	 * Return the attribute (if there is any) belonging to jpt1 and
	 * involved in a relation with jpt
	 */
	
	private Set<JavaPersistentAttribute> getRelAttributes(JavaPersistentType jpt,
			JavaPersistentType relJPT) {

		Set<JavaPersistentAttribute> res = new HashSet<JavaPersistentAttribute>();
		ListIterator<JavaPersistentAttribute> attIt = relJPT.attributes();
		while (attIt.hasNext()) {
			JavaPersistentAttribute at = attIt.next();
			IResource r = at.getParent().getResource();
			if (!r.exists())
				throw new RuntimeException();
			Annotation[] ans = getAnnotations(at);			
			String annotationName = null;
			for (Annotation an : ans) {
				annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName()); 
				if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(annotationName)) {
					String relTypeName = getRelTypeName((RelationshipMappingAnnotation)an, at.getResourcePersistentAttribute());
					if (!relTypeName.equals(jpt.getName()))
						continue;
					res.add(at);
				}
			}			
		}
		return res;
	}
	
	
	public void renameEntityClass(JavaPersistentType jpt, String newEntityName, IJPAEditorFeatureProvider fp) {
		renameEntityClass(fp.getCompilationUnit(jpt), newEntityName);
	}
		
	private void renameEntityClass(ICompilationUnit cu, String newName) {
		IType javaType = cu.findPrimaryType();
		renameType(javaType, newName);
	}
	
	private void renameType(IType type, String newName) {
		if (!type.exists())
			return;
		String oldName = type.getElementName();
		try {
			RenameSupport s = RenameSupport.create(type, newName, RenameSupport.UPDATE_REFERENCES);
			IWorkbenchWindow ww = JPADiagramEditorPlugin.getDefault()
					.getWorkbench().getActiveWorkbenchWindow();
			Shell sh = ww.getShell();
			s.perform(sh, ww);
		} catch (Exception e1) {
			JPADiagramEditorPlugin.logError("Cannot rename the type " + oldName, e1); //$NON-NLS-1$
		}
	}


	public JavaPersistentAttribute renameAttribute(JavaPersistentType jpt,
			String oldName, String newName, String inverseEntityName,
									 IJPAEditorFeatureProvider fp) throws InterruptedException {
		newName = JPAEditorUtil.decapitalizeFirstLetter(newName);
		if (JpaArtifactFactory.instance().isMethodAnnotated(jpt)) {		
			newName = JPAEditorUtil.produceValidAttributeName(newName);
		} 
		newName = JPAEditorUtil.produceUniqueAttributeName(jpt, newName);
		PersistenceUnit pu = null;
		JavaPersistentAttribute oldAt = jpt.getAttributeNamed(oldName);
		fp.addAddIgnore((JavaPersistentType)oldAt.getParent(), newName);
		JavaResourcePersistentAttribute jrpa = oldAt
				.getResourcePersistentAttribute();
		fp.addRemoveIgnore((JavaPersistentType)oldAt.getParent(), jrpa.getName());
		IRelation rel = fp.getRelationRelatedToAttribute(oldAt);
		String inverseJPAName = null;
		JavaPersistentType inverseJPT = null;
		if (BidirectionalRelation.class.isInstance(rel)) {
			inverseJPT = rel.getInverse();
			if (inverseJPT != oldAt.getParent()) {
				pu = JpaArtifactFactory.INSTANCE.getPersistenceUnit(jpt);
				inverseJPAName = rel.getInverseAttributeName();
			}
		}
		ICompilationUnit cu = fp.getCompilationUnit(jpt);
		renameAttribute(cu, oldName, newName, fp, this.isMethodAnnotated(jpt));
		refreshEntityModel(fp, jpt);
		JavaPersistentAttribute newAt = jpt.getAttributeNamed(newName);
		if (newAt == null)
			newAt = jpt.getAttributeNamed(JPAEditorUtil
					.revertFirstLetterCase(newName));
		int c = 0;
		while ((newAt == null) && (c < MAX_NUM_OF_ITERATIONS)) {
			c++;
			try {
				Thread.sleep(PAUSE_DURATION);
				newAt = jpt.getAttributeNamed(newName);
				if (newAt == null)
					newAt = (JavaPersistentAttribute) jpt
							.resolveAttribute(JPAEditorUtil
									.revertFirstLetterCase(newName));
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Thread.sleep() interrupted", e); //$NON-NLS-1$
				return null;
			}
		}
		if (newAt == null) {
			JPADiagramEditorPlugin.logError("The attribute " + newName + " could not be resolved", new NullPointerException()); //$NON-NLS-1$  //$NON-NLS-2$
		}
		fp.addRemoveIgnore(jpt, oldAt.getName());
		try {
			fp.replaceAttribute(oldAt, newAt);
		} catch (Exception e) {
			return newAt;
		}
		if (inverseJPAName != null) {
			fp.addAttribForUpdate(pu, inverseEntityName
					+ EntityChangeListener.SEPARATOR + inverseJPAName
					+ EntityChangeListener.SEPARATOR + newAt.getName());
			this.refreshEntityModel(fp, inverseJPT);
			Annotation a = rel.getInverseAnnotatedAttribute().getMapping().getMappingAnnotation();
			if (OwnableRelationshipMappingAnnotation.class.isInstance(a)) {
				boolean exce = true;
				int cnt = 0;
					while (exce && (cnt < 25)) {
					try {
						Thread.sleep(250);
						a = rel.getInverseAnnotatedAttribute().getMapping().getMappingAnnotation();
						((OwnableRelationshipMappingAnnotation)a).setMappedBy(newAt.getName());
						exce = false;
					} catch (Exception e) {}
					cnt++;
				}
			}
		}
		if (rel != null)
			updateRelation(jpt, fp, rel);
		
		return newAt;
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

	public JavaPersistentAttribute renameAttribute(JavaPersistentAttribute jpa,
			String newName, String newEntityName, IJPAEditorFeatureProvider fp) throws InterruptedException {
		return renameAttribute((JavaPersistentType)jpa.getParent(), jpa.getName(), newName,
				newEntityName, fp);
	}

	private void renameAttribute(ICompilationUnit cu, String oldName,
			String newName, IJPAEditorFeatureProvider fp, boolean isMethodAnnotated) throws InterruptedException {
		IType javaType = cu.findPrimaryType();
		if (javaType == null)
			return;
		IField attributeField = null;
		String typeSignature = null;
		if (isMethodAnnotated) {
			attributeField = javaType.getField(oldName);
			if (!attributeField.exists())
				attributeField =  javaType.getField(JPAEditorUtil.revertFirstLetterCase(oldName));
		} else {
			attributeField = javaType.getField(oldName);
		}
		String getterPrefix = "get";	//$NON-NLS-1$
		String methodName = getterPrefix + JPAEditorUtil.capitalizeFirstLetter(oldName); 	//$NON-NLS-1$
		IMethod getter = javaType.getMethod(methodName, new String[0]);
		if (!getter.exists()) {
			getterPrefix = "is";	//$NON-NLS-1$
		}
		methodName = getterPrefix + JPAEditorUtil.capitalizeFirstLetter(oldName); 	//$NON-NLS-1$
		getter = javaType.getMethod(methodName, new String[0]);		
		
		if (isMethodAnnotated) {
			try {
				typeSignature = getter.getReturnType();
			} catch (JavaModelException e1) {
				JPADiagramEditorPlugin.logError("Cannot obtain type signature of the getter of the attribute " + oldName, e1); //$NON-NLS-1$  
				return;
			}
			if ((typeSignature == null) || 
					(!"Z".equals(typeSignature) && !getterPrefix.equals("get"))) {		//$NON-NLS-1$ 	//$NON-NLS-2$ 
				JPADiagramEditorPlugin.logError("Cannot obtain type signature of the getter of the attribute " + oldName, new NullPointerException()); //$NON-NLS-1$  
				return;
			}
		} else {
			try {
				typeSignature = attributeField.getTypeSignature();
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Cannot obtain type signature of the field of the attribute " + oldName, e); //$NON-NLS-1$  
				return;
			}			
		}

		methodName = "set" + JPAEditorUtil.capitalizeFirstLetter(oldName); //$NON-NLS-1$
		IMethod setter = javaType.getMethod(methodName,
				new String[] { typeSignature });

		if (setter.exists())
			renameSetter(setter, newName);
		if (isMethodAnnotated) {
			if (attributeField.exists())
				renameField(attributeField, newName, isMethodAnnotated);			
			if (getter.exists())
				renameGetter(getter, newName);
		} else {
			if (getter.exists())
				renameGetter(getter, newName);
			if (attributeField.exists())
				renameField(attributeField, newName, isMethodAnnotated);
		}

	}

	private void renameField(IField field, String newName, boolean isMethodAnnotated) throws InterruptedException {
		if (!field.exists())
			return;
		String oldName = field.getElementName();
		if (oldName.equals(newName))
			return;
		try {
			RenameSupport s = RenameSupport.create(field, 
												   isMethodAnnotated ? JPAEditorUtil.decapitalizeFirstLetter(newName) : newName,
												   RenameSupport.UPDATE_REFERENCES);
			try {
				IWorkbenchWindow ww = JPADiagramEditorPlugin.getDefault()
						.getWorkbench().getActiveWorkbenchWindow();
				Shell sh = ww.getShell();
				s.perform(sh, ww);
			} catch (InvocationTargetException e) {
				JPADiagramEditorPlugin.logError("Cannot rename the field of the attribute " + oldName, e); //$NON-NLS-1$  
			}
		} catch (CoreException e1) {
			JPADiagramEditorPlugin.logError("Cannot rename the field of the attribute " + oldName, e1); //$NON-NLS-1$  
		}
	}

	private void renameGetter(IMethod getter, String newName) throws InterruptedException {
		if (!getter.exists())
			return;
		String oldName = getter.getElementName();
		String getterType = null;
		try {
			getterType = getter.getReturnType();
		} catch (JavaModelException e2) {
			JPADiagramEditorPlugin.logError("Can't obtain getter type", e2); //$NON-NLS-1$  
		}
		String newGetterName = ("Z".equals(getterType) ? "is" : "get") +		//$NON-NLS-1$	//$NON-NLS-2$	//$NON-NLS-3$
			JPAEditorUtil.capitalizeFirstLetter(newName);
		if (oldName.equals(newGetterName))
			return;
		try {
			RenameSupport s = RenameSupport.create(getter, newGetterName,
					RenameSupport.UPDATE_REFERENCES);
			try {
				IWorkbenchWindow ww = JPADiagramEditorPlugin.getDefault()
						.getWorkbench().getActiveWorkbenchWindow();
				Shell sh = ww.getShell();
				s.perform(sh, ww);
			} catch (InvocationTargetException e) {
				JPADiagramEditorPlugin.logError("Cannot rename the getter of the attribute " + oldName, e); //$NON-NLS-1$
			}
		} catch (CoreException e1) {
			JPADiagramEditorPlugin.logError("Cannot rename the getter of the attribute " + oldName, e1); //$NON-NLS-1$
		}
	}
		
	private void renameSetter(IMethod setter, String newName) throws InterruptedException {
		if (!setter.exists())
			return;
		String oldName = setter.getElementName();
		String newSetterName = "set"			//$NON-NLS-1$
			+ JPAEditorUtil.capitalizeFirstLetter(newName);
		if (oldName.equals(newSetterName))
			return;
		try {
			RenameSupport s = RenameSupport.create(setter, newSetterName,
					RenameSupport.UPDATE_REFERENCES);
			try {
				IWorkbenchWindow ww = JPADiagramEditorPlugin.getDefault()
						.getWorkbench().getActiveWorkbenchWindow();
				Shell sh = ww.getShell();
				s.perform(sh, ww);
			} catch (InvocationTargetException e) {
				JPADiagramEditorPlugin.logError("Cannot rename the setter of the attribute " + oldName, e); //$NON-NLS-1$
			}
		} catch (CoreException e1) {
			JPADiagramEditorPlugin.logError("Cannot rename the setter of the attribute " + oldName, e1); //$NON-NLS-1$
		}
	}

	private IRelation produceRelation(JavaPersistentAttribute persistentAttribite, Annotation an,
			JavaPersistentType relJPT, IJPAEditorFeatureProvider fp) {

		Hashtable<JavaPersistentAttribute, Annotation> ht = getRelAttributeAnnotation(
				persistentAttribite, relJPT);
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
	
	private Hashtable<JavaPersistentAttribute, Annotation> getRelAttributeAnnotation(
			JavaPersistentAttribute jpa, JavaPersistentType relJPT) {
		
		JavaPersistentType jpt = (JavaPersistentType)jpa.getParent();
		JpaArtifactFactory.instance().refreshEntityModel(null, jpt);
		ListIterator<JavaPersistentAttribute> attIt = relJPT.attributes();
		while (attIt.hasNext())	{
			JavaPersistentAttribute relEntAt = attIt.next();
			IResource r = relEntAt.getParent().getResource();
			if (!r.exists())
				throw new RuntimeException();
			JavaResourcePersistentAttribute relJRPA = relEntAt.getResourcePersistentAttribute();
			Annotation[] ans = this.getAnnotations(relEntAt);
			for (Annotation an : ans) {
				String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
				if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(annotationName)) {
					String relTypeName = getRelTypeName((RelationshipMappingAnnotation)an, relJRPA);					
					if (!relTypeName.equals(jpt.getName())) 
						continue;														
					JavaAttributeMapping mp = relEntAt.getMapping();
					if(mp.getMappingAnnotation() == null) {
						JpaArtifactFactory.instance().refreshEntityModel(null, (JavaPersistentType)relEntAt.getParent());
						mp = relEntAt.getMapping();
					}
					if (!OwnableRelationshipMappingAnnotation.class.isInstance(mp.getMappingAnnotation()))
						continue;
					String mappedBy = ((OwnableRelationshipMappingAnnotation)mp.getMappingAnnotation()).getMappedBy();
					if (!jpa.getName().equals(mappedBy)) 
						continue;
					Hashtable<JavaPersistentAttribute, Annotation> ht = new Hashtable<JavaPersistentAttribute, Annotation>();
					ht.put(relEntAt, an);
					return ht;					
				}
			}
		}
		return null;
	}
			
			
			/*
			ASTNode nd = jrpa.getMember().getModifiedDeclaration()
					.getDeclaration();
			String annotationName = null;
			if ((nd instanceof MethodDeclaration)
					|| (nd instanceof FieldDeclaration)) {
				ListIterator<?> modfs = ((BodyDeclaration) nd).modifiers()
						.listIterator();
				while (modfs.hasNext()) {
					Object modf = modfs.next();
					if (modf instanceof Annotation) {
						Annotation an = (Annotation)modf;
						annotationName = an.getTypeName()
								.getFullyQualifiedName();
						annotationName = annotationName
								.substring(annotationName.lastIndexOf('.') + 1);
						if (JPAEditorConstants.RELATION_ANNOTATIONS
								.contains(annotationName)) {
							String relTypeName = getRelTypeName(an, jrpa);					
							if (!relTypeName.equals(jpt.getName())) 
								continue;														
							JavaAttributeMapping mp = at.getSpecifiedMapping();
							if (!OwnableRelationshipMappingAnnotation.class.isInstance(mp.getMappingAnnotation()))
								continue;
							String mappedBy = ((OwnableRelationshipMappingAnnotation)mp.getMappingAnnotation()).getMappedBy();
							if (!jpa.getName().equals(mappedBy)) 
								continue;
							Hashtable<JavaPersistentAttribute, Annotation> ht = new Hashtable<JavaPersistentAttribute, Annotation>();
							ht.put(at, an);
							return ht;
						}
					} 			
				}
			}
			*/			
	
	private UnidirectionalRelation produceUniDirRelation(
			JavaPersistentType jpt, JavaPersistentAttribute at, Annotation an,
			JavaPersistentType relJPT, IJPAEditorFeatureProvider fp) {
		
		if (isNonOwner(at) || !JPAEditorUtil.getCompilationUnit((JavaPersistentType) at.getParent()).exists())
			return null;
		String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
		UnidirectionalRelation res = null;
		String attrName = at.getName();
		if (annotationName.equals(JPAEditorConstants.ANNOTATION_ONE_TO_ONE)) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, RelType.ONE_TO_ONE,
					RelDir.UNI))
				res = new OneToOneUniDirRelation(fp, jpt, relJPT, attrName, false,
						fp.getCompilationUnit(jpt), fp
								.getCompilationUnit(relJPT));
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_ONE_TO_MANY)) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, RelType.ONE_TO_MANY,
					RelDir.UNI))
				res = new OneToManyUniDirRelation(fp, jpt, relJPT, attrName, false,
						fp.getCompilationUnit(jpt), fp
								.getCompilationUnit(relJPT));
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_MANY_TO_ONE)) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, RelType.MANY_TO_ONE,
					RelDir.UNI))
				res = new ManyToOneUniDirRelation(fp, jpt, relJPT, attrName, false,
						fp.getCompilationUnit(jpt), fp
								.getCompilationUnit(relJPT));
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_MANY_TO_MANY)) {
			if (!fp.doesRelationExist(jpt, relJPT, attrName, RelType.MANY_TO_MANY,
					RelDir.UNI))
				res = new ManyToManyUniDirRelation(fp, jpt, relJPT, attrName,
						false, fp.getCompilationUnit(jpt), fp
								.getCompilationUnit(relJPT));
		}
		if (res != null)
			res.setAnnotatedAttribute(at);
		return res;
	}
	
	private BidirectionalRelation produceBiDirRelation(JavaPersistentType jpt,
			JavaPersistentAttribute at, Annotation an,
			JavaPersistentType relJPT, JavaPersistentAttribute relAt,
			Annotation relAn, IJPAEditorFeatureProvider fp) {
		JpaArtifactFactory.instance().refreshEntityModel(null, (JavaPersistentType)relAt.getParent());
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
			if (m.getMappingAnnotation()==null) {
				JpaArtifactFactory.instance().refreshEntityModel(null, (JavaPersistentType)relAt.getParent());
				m = relAt.getMapping();
			}
			if(m.getMappingAnnotation() instanceof OwnableRelationshipMappingAnnotation) {
		
			String mappedBy = ((OwnableRelationshipMappingAnnotation)m.getMappingAnnotation()).getMappedBy();
			if (mappedBy == null)
				return null;
			if (!mappedBy.equals(ownerAttrName))
				return null;
			}
		}
		
		BidirectionalRelation res = null;
		if (annotationName.equals(JPAEditorConstants.ANNOTATION_ONE_TO_ONE)) {
			if (!fp.doesRelationExist(jpt, relJPT, ownerAttrName, RelType.ONE_TO_ONE,
					RelDir.BI))
				res = new OneToOneBiDirRelation(fp, jpt, relJPT, ownerAttrName,
						inverseAttrName, false, fp.getCompilationUnit(jpt), fp
								.getCompilationUnit(relJPT));
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_MANY_TO_ONE)) {
			if (!fp.doesRelationExist(jpt, relJPT, ownerAttrName, RelType.MANY_TO_ONE,
					RelDir.BI))
				res = new ManyToOneBiDirRelation(fp, jpt, relJPT, ownerAttrName,
						inverseAttrName, false, fp.getCompilationUnit(jpt), fp
								.getCompilationUnit(relJPT));
		} else if (annotationName
				.equals(JPAEditorConstants.ANNOTATION_MANY_TO_MANY)) {
			if (!fp.doesRelationExist(jpt, relJPT, ownerAttrName, RelType.MANY_TO_MANY,
					RelDir.BI))
				res = new ManyToManyBiDirRelation(fp, jpt, relJPT, ownerAttrName,
						inverseAttrName, false, fp.getCompilationUnit(jpt), fp
								.getCompilationUnit(relJPT));
		}
		if (res != null) {
			res.setOwnerAnnotatedAttribute(at);
			res.setInverseAnnotatedAttribute(relAt);
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
	
	private String genGetterContents(String attrName, String attrType,
			String[] attrTypeElementNames, String actName,
			List<String> annotations, boolean isCollection) {
		
		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(Locale.ENGLISH)
				+ actName.substring(1);
		String contents = ""; //$NON-NLS-1$
		if (annotations != null) {
			Iterator<String> it = annotations.iterator();
			while (it.hasNext()) {
				String an = it.next();
				contents += "   " + an + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		if (isCollection) {
			contents += "    public Collection<"+ attrType + "> get" + attrNameWithCapitalA + "() {\n" +  	//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
					"        return "	//$NON-NLS-1$
					+ JPAEditorUtil.decapitalizeFirstLetter(actName) + ";\n" + //$NON-NLS-1$ 
			  "    }\n";  //$NON-NLS-1$			
		} else {
			contents += "    public "+ attrType + //$NON-NLS-1$
							((attrTypeElementNames == null)?"":("<" + JPAEditorUtil.createCommaSeparatedListOfSimpleTypeNames(attrTypeElementNames) + ">")) + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							(attrType.equals("boolean") ? " is" : " get") + attrNameWithCapitalA + "() {\n" +  	//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ 
					"        return "	//$NON-NLS-1$
					+ JPAEditorUtil.decapitalizeFirstLetter(actName) + ";\n" + //$NON-NLS-1$ 
			  "    }\n";  																//$NON-NLS-1$			
		}
		return contents;
	}
	
	private String genSetterContents(String attrName, String attrType,
			String[] attrTypeElementNames, String actName, boolean isCollection) {
		
		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(Locale.ENGLISH)
				+ actName.substring(1);
		String contents = ""; //$NON-NLS-1$
		if (isCollection) {
			contents = "    public void set" + attrNameWithCapitalA + "(Collection<" + attrType + "> param) " + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					"{\n" + //$NON-NLS-1$ 
					"        this."	//$NON-NLS-1$
					+ JPAEditorUtil.decapitalizeFirstLetter(actName)
					+ " = param;\n" + //$NON-NLS-1$ 
						  "    }\n";  	//$NON-NLS-1$
		} else {
			contents = "    public void set" + attrNameWithCapitalA + "(" + attrType + //$NON-NLS-1$ //$NON-NLS-2$
								((attrTypeElementNames == null)?"":("<" + JPAEditorUtil.createCommaSeparatedListOfSimpleTypeNames(attrTypeElementNames) + ">")) + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					" param) {\n"	//$NON-NLS-1$
					+ 
					"        this."	//$NON-NLS-1$
					+ JPAEditorUtil.decapitalizeFirstLetter(actName)
					+ " = param;\n" + //$NON-NLS-1$ 
			  "    }\n";  			//$NON-NLS-1$			
		}
		return contents;
	}
	
	private String genGetterWithAppropriateType(String attrName, String attrType,
			String actName, String type) {
		return genGetterWithAppropriateType(attrName, null, attrType,
				actName, type);
	}

	
	private String genGetterWithAppropriateType(String attrName, String mapKeyType, String attrType,
			String actName, String type) {

		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(
				Locale.ENGLISH)
				+ actName.substring(1);
		String contents = "    public " + JPAEditorUtil.returnSimpleName(type) + 		//$NON-NLS-1$
				"<" + ((mapKeyType != null) ? (mapKeyType + ", ") : "")  +  attrType + "> " +	//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"get" + attrNameWithCapitalA + "() {\n" + 	//$NON-NLS-1$ //$NON-NLS-2$
				"        return " 	//$NON-NLS-1$
				+ JPAEditorUtil.decapitalizeFirstLetter(actName) + ";\n" + 		//$NON-NLS-1$
				"    }\n"; 	//$NON-NLS-1$
		return contents;
	}

	private String genSetterWithAppropriateType(String attrName, String attrType,
			String actName, String type) {
		return genSetterWithAppropriateType(attrName, null, attrType,
				actName, type);
	}
	
	private String genSetterWithAppropriateType(String attrName, String mapKeyType, String attrType,
			String actName, String type) {

		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(
				Locale.ENGLISH)
				+ actName.substring(1);
		String contents = "    public void set" + attrNameWithCapitalA + 			//$NON-NLS-1$
				"(" + JPAEditorUtil.returnSimpleName(type) + 						//$NON-NLS-1$
				"<" + ((mapKeyType != null) ? (mapKeyType + ", ") : "") + attrType + "> param) " +	//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"{\n" +   	//$NON-NLS-1$
				"        this." 	//$NON-NLS-1$
				+ JPAEditorUtil.decapitalizeFirstLetter(actName)
				+ " = param;\n" + 	//$NON-NLS-1$
				"    }\n"; 			//$NON-NLS-1$
		return contents;
	}
	
	/*
	private String returnSimpleName(String input) {
		String name = input;
		if (name.lastIndexOf('.') != -1) {
			name = name.substring(name.lastIndexOf('.') + 1);
		}
		return name;
	}
	*/
	
	private JavaPersistentAttribute getAttributeFromEntity(
			JavaPersistentType jpt, String attributeName) {
		this.refreshEntityModel(null, jpt);
		PersistentAttribute at = jpt.getAttributeNamed(attributeName);
		if (at == null) {
			jpt.getResourcePersistentType().synchronizeWith(jpt.getResourcePersistentType().getJavaResourceCompilationUnit().buildASTRoot());
			jpt.update();
		}
		int c = 0;
		while ((at == null) && (c < MAX_NUM_OF_ITERATIONS)) {		
			try {
				Thread.sleep(PAUSE_DURATION);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Cannot get the attribute " + //$NON-NLS-1$
						attributeName + " from " + jpt.getName(), e); //$NON-NLS-1$
			}
			at = jpt.getAttributeNamed(attributeName);
			c++;
		}
		return (JavaPersistentAttribute)at;		
	}
	
	private boolean doesAttributeExist(JavaPersistentType jpt, String name)
											throws JavaModelException {
		boolean exists = false;
		if (jpt.resolveAttribute(name) != null) {
			return true;
		}
		return exists;
	}
		
	public JavaResourcePersistentType convertJPTToJRPT(JavaPersistentType jpt) {
		if (jpt == null) 
			return null;
		return jpt.getJpaProject().getJavaResourcePersistentType(jpt.getName());
	}
	
	public PersistenceUnit getPersistenceUnit(JpaFile jpaFile) {
		JpaProject jpaProject  = jpaFile.getJpaProject();
		if (jpaProject == null)
			return null;
		return getPersistenceUnit(jpaProject); 
	}
	
	public PersistenceUnit getPersistenceUnit(JpaProject project) {
		if(project.getRootContextNode().getPersistenceXml() == null)
			return null;
		return project.getRootContextNode().getPersistenceXml().getPersistence()
				.persistenceUnits().next();
	}
	
	public PersistenceUnit getPersistenceUnit(JavaPersistentType jpt) {
		return jpt.getPersistenceUnit(); 
	}
	
	public boolean isMethodAnnotated(JavaPersistentAttribute attr) {
		return !attr.getResourcePersistentAttribute().isField();
	}
	
	public boolean isMethodAnnotated(JavaPersistentType jpt) {
		ListIterator<JavaPersistentAttribute> li = jpt.attributes();
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
		JavaResourcePersistentType jrpt = convertJPTToJRPT(jpt);
		if (jrpt == null)
			return null; 
		TableAnnotation tan = (TableAnnotation)jrpt.getAnnotation("javax.persistence.Table"); //$NON-NLS-1$
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
		JavaResourcePersistentType jrpt = convertJPTToJRPT(jpt);
		if (jrpt == null) {
			return; 
		}
		TableAnnotation ta = (TableAnnotation)jrpt.getAnnotation("javax.persistence.Table");	//$NON-NLS-1$
		if (ta != null) 
			ta.setName(tableName);		
	}
	
	/*
	private Object extractAnnotationMemberValue(Annotation an, String memberName) {
		
		an.
		
		IMemberValuePair[] mvps;
		try {
			mvps = an.getMemberValuePairs();
		} catch (JavaModelException e) {
			tracer.error("Can't get annotation members", e);	//$NON-NLS-1$
			return null;
		}
		for (IMemberValuePair mvp : mvps) {
			if (mvp.getMemberName().equals(memberName)) {
				return mvp.getValue();
			}
		}
		return null;
	}
	*/	

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
	
	private void addNewRelations(IJPAEditorFeatureProvider fp,
			JavaPersistentType jpt) {
	
		Collection<IRelation> selfRels = new HashSet<IRelation>(); 
		Collection<IRelation> newRels = JpaArtifactFactory.instance()
				.produceAllRelations(jpt, fp);
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
	
	private void addNewRelation(IJPAEditorFeatureProvider fp, IRelation rel) {
		AddConnectionContext ctx = new AddConnectionContext(JPAEditorUtil
				.getAnchor(rel.getOwner(), fp), JPAEditorUtil.getAnchor(rel
				.getInverse(), fp));
		ctx.setNewObject(rel);
		ctx.setTargetContainer(fp.getDiagramTypeProvider().getDiagram());
		refreshEntityModel(fp, rel.getOwner());
		refreshEntityModel(fp, rel.getInverse());
		AddRelationFeature ft = new AddRelationFeature(fp);
		ft.add(ctx);		
	}
	
	private String getRelTypeName(RelationshipMappingAnnotation an,
			JavaResourcePersistentAttribute jrpa) {
		String relTypeName = null;
		try {
			boolean isMap = jrpa.getTypeName().equals("java.util.Map");	//$NON-NLS-1$
			relTypeName = jrpa.getTypeTypeArgumentName(isMap ? 1 : 0);
		} catch (Exception e) {}
		if (relTypeName == null) 
			relTypeName = an.getFullyQualifiedTargetEntityClassName();												
		if (relTypeName == null) 
			relTypeName = JPAEditorUtil.getAttributeTypeName(jrpa);							
		return relTypeName;
	}
		
	public JpaProject getJpaProject(IProject project) throws CoreException {
		return JptJpaCorePlugin.getJpaProject(project);
	}
	
	public String getIdType(JavaPersistentType jpt) {
		IdClassAnnotation an = (IdClassAnnotation)jpt.getResourcePersistentType().getAnnotation(IdClassAnnotation.ANNOTATION_NAME);
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
		ListIterator<JavaPersistentAttribute> attribsIter = jpt.attributes();
		ArrayList<JavaPersistentAttribute> res = new ArrayList<JavaPersistentAttribute>();
		while (attribsIter.hasNext()) {
			JavaPersistentAttribute at = attribsIter.next();
			if (isId(at))
				res.add(at);
		}
		JavaPersistentAttribute[] ret = new JavaPersistentAttribute[res.size()];
		return res.toArray(ret);
	}
	
	public boolean isId(JavaPersistentAttribute jpa) {
		return isSimpleId(jpa) || isEmbeddedId(jpa);
	}
	
	public boolean isSimpleId(JavaPersistentAttribute jpa) {
		IdAnnotation an = (IdAnnotation)jpa.getResourcePersistentAttribute().getAnnotation(IdAnnotation.ANNOTATION_NAME);
		return (an != null);
	}
	
	public boolean isEmbeddedId(JavaPersistentAttribute jpa) {
		EmbeddedIdAnnotation an = (EmbeddedIdAnnotation)jpa.getResourcePersistentAttribute().getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME);
		return (an != null);
	}	
	
	public String getColumnName(JavaPersistentAttribute jpa) {
		String columnName= null;
		ColumnAnnotation an = (ColumnAnnotation)jpa.
									getResourcePersistentAttribute().
										getAnnotation(ColumnAnnotation.ANNOTATION_NAME);
		if (an != null) 
			columnName = an.getName();
		if (columnName == null) 
			columnName = jpa.getName();		
		return columnName; 
	}
	

}