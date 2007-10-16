/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumArrayDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitArrayAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.utility.internal.CollectionTools;


public abstract class AbstractRelationshipMappingAnnotation extends AbstractAnnotationResource<Attribute> implements RelationshipMappingAnnotation
{
	private final AnnotationElementAdapter<String> targetEntityAdapter;

	private final AnnotationElementAdapter<String> fetchAdapter;

	private final AnnotationElementAdapter<String[]> cascadeAdapter;

	private String targetEntity;

	private String fullyQualifiedTargetEntity;

	private FetchType fetch;
	
	private CascadeType[] cascadeTypes;
	
	public AbstractRelationshipMappingAnnotation(JavaPersistentAttributeResource parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		super(parent, attribute, daa);
		this.targetEntityAdapter = buildAnnotationElementAdapter(targetEntityAdapter());
		this.fetchAdapter = buildAnnotationElementAdapter(fetchAdapter());
		this.cascadeAdapter = new ShortCircuitArrayAnnotationElementAdapter<String>(attribute, cascadeAdapter());
	}
	
	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.getMember(), daea);
	}

	/**
	 * return the Java adapter's 'targetEntity' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> targetEntityAdapter();

	/**
	 * return the Java adapter's 'cascade' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String[]> cascadeAdapter();

	/**
	 * return the Java adapter's 'fetch' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> fetchAdapter();

	public String getTargetEntity() {
		return this.targetEntity;
	}
	
	public void setTargetEntity(String targetEntity) {
		this.targetEntity = targetEntity;
		this.targetEntityAdapter.setValue(targetEntity);
	}
	
	public String getFullyQualifiedTargetEntity() {
		return this.fullyQualifiedTargetEntity;
	}
	
	private void setFullyQualifiedTargetEntity(String targetEntity) {
		this.fullyQualifiedTargetEntity = targetEntity;
		//change notification
	}
	
	public FetchType getFetch() {
		return this.fetch;
	}
	
	public void setFetch(FetchType fetch) {
		this.fetch = fetch;
		this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(fetch));
	}
		
	public boolean isCascadeAll() {
		return CollectionTools.contains(this.cascadeTypes, CascadeType.ALL);
	}
	
	public void setCascadeAll(boolean all) {
		setCascade(all, CascadeType.ALL);
	}
	
	public boolean isCascadePersist() {
		return CollectionTools.contains(this.cascadeTypes, CascadeType.PERSIST);
	}
	
	public void setCascadePersist(boolean persist) {
		setCascade(persist, CascadeType.PERSIST);
	}
	
	public boolean isCascadeMerge() {
		return CollectionTools.contains(this.cascadeTypes, CascadeType.MERGE);
	}
	
	public void setCascadeMerge(boolean merge) {
		setCascade(merge, CascadeType.MERGE);
	}
	
	public boolean isCascadeRemove() {
		return CollectionTools.contains(this.cascadeTypes, CascadeType.REMOVE);
	}
	
	public void setCascadeRemove(boolean remove) {
		setCascade(remove, CascadeType.REMOVE);
	}
	
	public boolean isCascadeRefresh() {
		return CollectionTools.contains(this.cascadeTypes, CascadeType.REFRESH);
	}
	
	public void setCascadeRefresh(boolean refresh) {
		setCascade(refresh, CascadeType.REFRESH);
	}
		
	private void addCascadeType(CascadeType cascadeType) {
		List<CascadeType> cascadeCollection = CollectionTools.list(this.cascadeTypes);
		cascadeCollection.add(cascadeType);
		setCascadeTypes(cascadeCollection.toArray(new CascadeType[cascadeCollection.size()]));
	}
	
	private void removeCascadeType(CascadeType cascadeType) {
		List<CascadeType> cascadeCollection = CollectionTools.list(this.cascadeTypes);
		cascadeCollection.remove(cascadeType);
		setCascadeTypes(cascadeCollection.toArray(new CascadeType[cascadeCollection.size()]));
	}
	
	private void setCascadeTypes(CascadeType[] cascadeTypes) {
		this.cascadeTypes = cascadeTypes;
		String[] newJavaValue = CascadeType.toJavaAnnotationValue(cascadeTypes);
		this.cascadeAdapter.setValue(newJavaValue);
	}
	
	private void setCascade(boolean isSet, CascadeType cascadeType) {
		List<CascadeType> cascadeCollection = CollectionTools.list(this.cascadeTypes);
		if (cascadeCollection.contains(cascadeType)) {
			if (!isSet) {
				removeCascadeType(cascadeType);
			}
		}
		else {
			if (isSet) {
				addCascadeType(cascadeType);
			}
		}
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		this.setFetch(FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot)));
		this.setTargetEntity(this.targetEntityAdapter.getValue(astRoot));
		this.setFullyQualifiedTargetEntity(fullyQualifiedTargetEntity(astRoot));
		updateCascadeFromJava(astRoot);
	}
	
	private void updateCascadeFromJava(CompilationUnit astRoot) {
		String[] javaValue = this.cascadeAdapter.getValue(astRoot);
		setCascadeTypes(CascadeType.fromJavaAnnotationValue(javaValue));
	}
	
	private String fullyQualifiedTargetEntity(CompilationUnit astRoot) {
		if (getTargetEntity() == null) {
			return null;
		}
		return JDTTools.resolveFullyQualifiedName(this.targetEntityAdapter.expression(astRoot));
	}

	// ********** static methods **********
	
	protected static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		// TODO what about QualifiedType?
		return buildAnnotationElementAdapter(annotationAdapter, elementName, SimpleTypeStringExpressionConverter.instance());
	}
	
	protected static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, converter);
	}
	
	protected static DeclarationAnnotationElementAdapter<String> buildFetchAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumDeclarationAnnotationElementAdapter(annotationAdapter, elementName, false);
	}
	
	protected static DeclarationAnnotationElementAdapter<String[]> buildEnumArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumArrayDeclarationAnnotationElementAdapter(annotationAdapter, elementName, false);
	}

}
