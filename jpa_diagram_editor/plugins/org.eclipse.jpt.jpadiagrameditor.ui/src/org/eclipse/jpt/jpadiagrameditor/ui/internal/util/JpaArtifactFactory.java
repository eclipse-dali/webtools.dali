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
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandExecutor;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.iterables.SubListIterableWrapper;
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
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableAnnotation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.AddAttributeCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.CreateNewAttributeCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.DeleteAttributeCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.RenameAttributeCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.RenameEntityCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.SetMappedByNewValueCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddInheritedEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.UpdateAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
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
			setMappedByAnnotationAttribute(resolvedAttribute, referencedJPT, ownerAttibute);
		}

	}
	
	public void addManyToOneRelation(IFeatureProvider fp, JavaPersistentType manySideJPT,
			JavaPersistentAttribute manySideAttribute, JavaPersistentType singleSideJPT,
			JavaPersistentAttribute singleSideAttibute, int direction, boolean isMap) {
		
		setMappingKeyToAttribute(fp, manySideJPT, manySideAttribute, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);

		if (direction == JPAEditorConstants.RELATION_TYPE_UNIDIRECTIONAL)
			return;
		
		JavaPersistentAttribute resolvedSingleSideAttribute = setMappingKeyToAttribute(fp, singleSideJPT, singleSideAttibute, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		setMappedByAnnotationAttribute(resolvedSingleSideAttribute, singleSideJPT, manySideAttribute);
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
			setMappedByAnnotationAttribute(resolvedSingleSideAttribute, singleSideJPT, manySideAttribute);			
		} else {
			addJoinColumnIfNecessary(resolvedSingleSideAttribute, singleSideJPT, fp);
		}		
		if (isMap)
			singleSideAttibute.getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}
	
	private void setMappedByAnnotationAttribute(JavaPersistentAttribute resolvedAttr, JavaPersistentType type1, JavaPersistentAttribute jpa){

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
		((OwnableRelationshipMappingAnnotation)annotation).setMappedBy(jpa.getName());
	}

	private JavaPersistentAttribute setMappingKeyToAttribute(IFeatureProvider fp, JavaPersistentType jpt, JavaPersistentAttribute jpa, String mappingKey){
		JavaPersistentAttribute resolvedManySideAttribute = (JavaPersistentAttribute) jpt.resolveAttribute(jpa.getName());
		resolvedManySideAttribute.setMappingKey(mappingKey);
		return resolvedManySideAttribute;
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
			setMappedByAnnotationAttribute(resolvedInverseSideAttribute, inverseSideJPT, ownerSideAttribute);
			
			if (isMap)
				resolvedInverseSideAttribute.getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		}		
		
	}
		
	public void restoreEntityClass(JavaPersistentType jpt,
			IJPAEditorFeatureProvider fp) {
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
	
	public boolean hasEntityOrMappedSuperclassAnnotation(JavaPersistentType jpt) {
		return hasEntityAnnotation(jpt) || hasMappedSuperclassAnnotation(jpt); 
	}
	
	public boolean hasEntityAnnotation(JavaPersistentType jpt) {
		return (jpt.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY);
	}	
	
	public boolean hasMappedSuperclassAnnotation(JavaPersistentType jpt) {
		return (jpt.getMappingKey() == MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
	}		
	
	public String getSpecifiedEntityName(JavaPersistentType jpt){
		JavaTypeMapping jtm = jpt.getMapping();
		if (jtm instanceof JavaEntity) {
			JavaEntity gje = (JavaEntity)jtm;
		return gje.getSpecifiedName();
	}
		JavaMappedSuperclass jms = (JavaMappedSuperclass)jtm;
		return jms.getName();
	}
	
	public void renameEntity(JavaPersistentType jpt, String newName) {
		JavaTypeMapping jtm = jpt.getMapping();
		if (jtm instanceof JavaEntity) {
			JavaEntity gje = (JavaEntity)jtm;
		gje.setSpecifiedName(newName);	
	}
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
		
		fp.getDiagramTypeProvider().getDiagramEditor().selectPictogramElements(new PictogramElement[] {});
		
		try {
			if (doesAttributeExist(jpt, actName)) {
				return (JavaPersistentAttribute) jpt
						.resolveAttribute(attributeName);
			}

		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannnot create a new attribute with name " + attributeName, e); //$NON-NLS-1$				
		}
		
		Command addAttributeCommand = new AddAttributeCommand(fp, jpt, attributeType, mapKeyType, attributeName, actName, isCollection, cu1, cu2);
		try {
			getJpaProjectManager().execute(addAttributeCommand, SynchronousUiCommandExecutor.instance());
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot add a new attribute with name " + actName, e); //$NON-NLS-1$		
		}
		
		if(jpt.getAttributeNamed(attributeName) == null){
			System.out.println("bahhhhhh go +++++++++++++ " + attributeName);
			jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		}
		JavaPersistentAttribute res = jpt.getAttributeNamed(actName);
		if(res == null){
			System.out.println("6ti eba majkata ====================is");
			jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
			res = jpt.getAttributeNamed(actName);
		}
		return res;
	}
	
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
		
		fp.getDiagramTypeProvider().getDiagramEditor().selectPictogramElements(new PictogramElement[] {});
		
		Command createNewAttributeCommand = new CreateNewAttributeCommand(jpt, cu, attrName, attrTypeName, attrTypes, actName, annotations, isCollection, isMethodAnnotated);
		try {
			getJpaProjectManager().execute(createNewAttributeCommand, SynchronousUiCommandExecutor.instance());
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot create a new attribute with name " + attrName, e); //$NON-NLS-1$		
		}

		JavaPersistentAttribute jpa = jpt.getAttributeNamed(attrName);		
		return jpa;
	}
		
	public void deleteAttribute(JavaPersistentType jpt, String attributeName,
								IJPAEditorFeatureProvider fp) {
		
		Command deleteAttributeCommand = new DeleteAttributeCommand(jpt, attributeName, fp);
		try {
			getJpaProjectManager().execute(deleteAttributeCommand, SynchronousUiCommandExecutor.instance());
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot delete attribute with name " + attributeName, e); //$NON-NLS-1$		
		}
	}
	
	private String genUniqueAttrName(JavaPersistentType jpt, 
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
		
	public Annotation[] getAnnotations(JavaPersistentAttribute persistentAttribite) {	
		JavaResourceAttribute jrpt = persistentAttribite.getResourceAttribute();
		Annotation[] res = new Annotation[jrpt.getAnnotationsSize()];
		                                  //mappingAnnotationsSize() + jrpt.supportingAnnotationsSize()];
		int c = 0;
		for (Annotation annotation : jrpt.getAnnotations()) {
			res[c] = annotation;
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
		
		JavaResourceAttribute jrpt = persistentAttribite.getResourceAttribute();
		HashSet<String> res = new HashSet<String>();
		for (Annotation annotation : jrpt.getAnnotations()) {
			res.add(JPAEditorUtil.returnSimpleName(annotation.getAnnotationName()));
		}
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
		CompilationUnit jdtCU = jpt.getJavaResourceType().getJavaResourceCompilationUnit().buildASTRoot();
		JavaResourceAttribute jrpt = persistentAttribite.getResourceAttribute();
		List<String> res = new LinkedList<String>();
		for (Annotation an : jrpt.getAnnotations()) {
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
		
		HashSet<IRelation> res = new HashSet<IRelation>();
		for (JavaPersistentAttribute at : newJPT.getAttributes()) {
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
		
		JavaResourceAttribute jrpa = persistentAttribite
				.getResourceAttribute();
		
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
		IRelation res = null;
		for (JavaPersistentAttribute at : jpt1.getAttributes()) {
			IResource r = at.getParent().getResource();
			if (!r.exists())
				throw new RuntimeException();
			try {
				JavaResourceAttribute jrpa = at.getResourceAttribute();
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

	/*
	 * Return the attribute (if there is any) belonging to jpt1 and
	 * involved in a relation with jpt
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
				if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(annotationName)) {
					String relTypeName = getRelTypeName((RelationshipMappingAnnotation)an, at.getResourceAttribute());
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
			getJpaProjectManager().execute(renameEntityCommand, SynchronousUiCommandExecutor.instance());
		} catch (InterruptedException e) {
			JPADiagramEditorPlugin.logError("Cannot rename entity " + jpt.getName(), e); //$NON-NLS-1$		
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
		JavaResourceAttribute jra = oldAt
				.getResourceAttribute();
		fp.addRemoveIgnore((JavaPersistentType)oldAt.getParent(), jra.getName());
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

		Command renameAttributeCommand = new RenameAttributeCommand(jpt, oldName, newName, fp);
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
		if (inverseAttributeName != null) {
			Command changeMappedByValueCommand = new SetMappedByNewValueCommand(fp, pu, inverseEntityName, inverseAttributeName, newAt, rel);
			getJpaProjectManager().execute(changeMappedByValueCommand, SynchronousUiCommandExecutor.instance());
		}
		if (rel != null)
			updateRelation(jpt, fp, rel);
		
		return newAt;
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

	public JavaPersistentAttribute renameAttribute(JavaPersistentAttribute jpa,
			String newName, String newEntityName, IJPAEditorFeatureProvider fp) throws InterruptedException {
		return renameAttribute((JavaPersistentType)jpa.getParent(), jpa.getName(), newName,
				newEntityName, fp);
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
		for (JavaPersistentAttribute relEntAt : relJPT.getAttributes())	{
			IResource r = relEntAt.getParent().getResource();
			if (!r.exists())
				throw new RuntimeException();
			JavaResourceAttribute relJRA = relEntAt.getResourceAttribute();
			Annotation[] ans = this.getAnnotations(relEntAt);
			for (Annotation an : ans) {
				String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
				if (JPAEditorConstants.RELATION_ANNOTATIONS.contains(annotationName)) {
					String relTypeName = getRelTypeName((RelationshipMappingAnnotation)an, relJRA);					
					if (!relTypeName.equals(jpt.getName())) 
						continue;														
					JavaAttributeMapping mp = relEntAt.getMapping();
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
	
	private IUnidirectionalRelation produceUniDirRelation(
			JavaPersistentType jpt, JavaPersistentAttribute at, Annotation an,
			JavaPersistentType relJPT, IJPAEditorFeatureProvider fp) {
		
		if (isNonOwner(at) || !JPAEditorUtil.getCompilationUnit((JavaPersistentType) at.getParent()).exists())
			return null;
		String annotationName = JPAEditorUtil.returnSimpleName(an.getAnnotationName());
		IUnidirectionalRelation res = null;
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
			if (!mappedBy.equals(ownerAttrName))
				return null;
			}
		}
		
		IBidirectionalRelation res = null;
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
	
	public String genGetterContents(String attrName, String attrType,
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
	
	public String genSetterContents(String attrName, String attrType,
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
		return (JavaResourceType) jpt.getJpaProject().getJavaResourceType(jpt.getName(), Kind.TYPE);
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
		return project.getRootContextNode().getPersistenceXml().getRoot()
				.getPersistenceUnits().iterator().next();
	}
	
	public PersistenceUnit getPersistenceUnit(JavaPersistentType jpt) {
		return jpt.getPersistenceUnit(); 
	}
	
	public boolean isMethodAnnotated(JavaPersistentAttribute attr) {
		return attr.getResourceAttribute().getKind() == Kind.METHOD;
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
			String v = Graphiti.getPeService().getPropertyValue(conn, IsARelation.IS_A_CONNECTION_PROP_KEY);
			if (Boolean.TRUE.toString().equals(v))
				continue;
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
	
	private String getRelTypeName(RelationshipMappingAnnotation an,
			JavaResourceAttribute jra) {
		String relTypeName = null;
		try {
			boolean isMap = jra.getTypeBinding().getQualifiedName().equals(JPAEditorConstants.MAP_TYPE);
			relTypeName = jra.getTypeBinding().getTypeArgumentName(isMap ? 1 : 0);
		} catch (Exception e) {}
		if (relTypeName == null) 
			relTypeName = an.getFullyQualifiedTargetEntityClassName();												
		if (relTypeName == null) 
			relTypeName = JPAEditorUtil.getAttributeTypeName(jra);							
		return relTypeName;
	}
		
	public JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
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
			if (isId(at))
				res.add(at);
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

	
	public boolean isId(ReadOnlyPersistentAttribute jpa) {
		return isSimpleId(jpa) || isEmbeddedId(jpa);
	}
	
	public boolean isSimpleId(ReadOnlyPersistentAttribute jpa) {
		return (jpa.getMappingKey() == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
	}
	
	public boolean isEmbeddedId(ReadOnlyPersistentAttribute jpa) {
		return (jpa.getMappingKey() == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
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
		IColorConstant foreground = dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.MappedSupeclass) ? 
				JPAEditorConstants.MAPPED_SUPERCLASS_BORDER_COLOR:
				JPAEditorConstants.ENTITY_BORDER_COLOR;
		return foreground;
	}

	public IColorConstant getBackground(JPAEditorConstants.DIAGRAM_OBJECT_TYPE dot) {
		IColorConstant background = dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.MappedSupeclass) ? 
				JPAEditorConstants.MAPPED_SUPERCLASS_BACKGROUND:
				JPAEditorConstants.ENTITY_BACKGROUND;	
		return background;
	}
	
	public String getRenderingStyle(JPAEditorConstants.DIAGRAM_OBJECT_TYPE dot) {
		String renderingStyle = dot.equals(JPAEditorConstants.DIAGRAM_OBJECT_TYPE.MappedSupeclass) ? 
			IJPAEditorPredefinedRenderingStyle.GREEN_WHITE_GLOSS_ID :
			IJPAEditorPredefinedRenderingStyle.BLUE_WHITE_GLOSS_ID;
		return renderingStyle;
	}
	
	public JPAEditorConstants.DIAGRAM_OBJECT_TYPE determineDiagramObjectType(JavaPersistentType jpt) {
		if (this.hasEntityAnnotation(jpt)) {
			return JPAEditorConstants.DIAGRAM_OBJECT_TYPE.Entity;
		} else if (this.hasMappedSuperclassAnnotation(jpt)) {
			return JPAEditorConstants.DIAGRAM_OBJECT_TYPE.MappedSupeclass;
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
	
		
}
