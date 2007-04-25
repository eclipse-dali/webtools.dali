/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import java.util.List;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.wst.common.internal.emf.resource.ReadAheadHelper;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorPath;
import org.eclipse.wst.common.internal.emf.resource.VariableTranslatorFactory;

public class DebugTranslator extends Translator 
{
	@Override
	public String getDOMPath() {
		// TODO Auto-generated method stub
		return super.getDOMPath();
	}

	public DebugTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
		// TODO Auto-generated constructor stub
	}
	
	public DebugTranslator(String domNameAndPath, EStructuralFeature aFeature, int style) {
		super(domNameAndPath, aFeature, style);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		// TODO Auto-generated method stub
		return super.createEMFObject(nodeName, readAheadName);
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		// TODO Auto-generated method stub
		return super.getChildren(target, versionID);
	}
	
	@Override
	public String getDOMName(Object value) {
		// TODO Auto-generated method stub
		return super.getDOMName(value);
	}
	
	@Override
	public boolean isManagedByParent() {
		// TODO Auto-generated method stub
		return super.isManagedByParent();
	}

	@Override
	public void addReadAheadHelper(ReadAheadHelper helper) {
		// TODO Auto-generated method stub
		super.addReadAheadHelper(helper);
	}

	@Override
	public EObject basicGetDependencyObject(EObject parent) {
		// TODO Auto-generated method stub
		return super.basicGetDependencyObject(parent);
	}

	@Override
	public void clearList(EObject mofObject) {
		// TODO Auto-generated method stub
		super.clearList(mofObject);
	}

	@Override
	public Object convertStringToValue(String strValue, EObject owner) {
		// TODO Auto-generated method stub
		return super.convertStringToValue(strValue, owner);
	}

	@Override
	public Object convertStringToValue(String nodeName, String readAheadName,
			String value, Notifier owner) {
		// TODO Auto-generated method stub
		return super.convertStringToValue(nodeName, readAheadName, value, owner);
	}

	@Override
	public String convertValueToString(Object value, EObject owner) {
		// TODO Auto-generated method stub
		return super.convertValueToString(value, owner);
	}

	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		return super.equals(object);
	}

	@Override
	public String extractStringValue(EObject emfObject) {
		// TODO Auto-generated method stub
		return super.extractStringValue(emfObject);
	}

	@Override
	public boolean featureExists(EObject emfObject) {
		// TODO Auto-generated method stub
		return super.featureExists(emfObject);
	}

	@Override
	public Translator findChild(String tagName, Object target, int versionID) {
		// TODO Auto-generated method stub
		return super.findChild(tagName, target, versionID);
	}

	@Override
	protected Translator[] getChildren() {
		// TODO Auto-generated method stub
		return super.getChildren();
	}

	@Override
	public EStructuralFeature getDependencyFeature() {
		// TODO Auto-generated method stub
		return super.getDependencyFeature();
	}

	@Override
	public String[] getDOMNames() {
		// TODO Auto-generated method stub
		return super.getDOMNames();
	}

	@Override
	public EStructuralFeature getFeature() {
		// TODO Auto-generated method stub
		return super.getFeature();
	}

	@Override
	public List getMOFChildren(EObject mofObject) {
		// TODO Auto-generated method stub
		return super.getMOFChildren(mofObject);
	}

	@Override
	public Object getMOFValue(EObject mofObject) {
		// TODO Auto-generated method stub
		return super.getMOFValue(mofObject);
	}

	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return super.getNameSpace();
	}

	@Override
	public ReadAheadHelper getReadAheadHelper(String parentName) {
		// TODO Auto-generated method stub
		return super.getReadAheadHelper(parentName);
	}

	@Override
	public TranslatorPath[] getTranslatorPaths() {
		// TODO Auto-generated method stub
		return super.getTranslatorPaths();
	}

	@Override
	public Translator[] getVariableChildren(Notifier target, int version) {
		// TODO Auto-generated method stub
		return super.getVariableChildren(target, version);
	}

	@Override
	public VariableTranslatorFactory getVariableTranslatorFactory() {
		// TODO Auto-generated method stub
		return super.getVariableTranslatorFactory();
	}

	@Override
	public boolean hasDOMPath() {
		// TODO Auto-generated method stub
		return super.hasDOMPath();
	}

	@Override
	public boolean hasReadAheadNames() {
		// TODO Auto-generated method stub
		return super.hasReadAheadNames();
	}

	@Override
	protected void initializeDOMNameAndPath(String domNameAndPathArg) {
		// TODO Auto-generated method stub
		super.initializeDOMNameAndPath(domNameAndPathArg);
	}

	@Override
	public boolean isBooleanFeature() {
		// TODO Auto-generated method stub
		return super.isBooleanFeature();
	}

	@Override
	public boolean isBooleanUppercase() {
		// TODO Auto-generated method stub
		return super.isBooleanUppercase();
	}

	@Override
	public boolean isCDATAContent() {
		// TODO Auto-generated method stub
		return super.isCDATAContent();
	}

	@Override
	public boolean isComment() {
		// TODO Auto-generated method stub
		return super.isComment();
	}

	@Override
	public boolean isDataType() {
		// TODO Auto-generated method stub
		return super.isDataType();
	}

	@Override
	public boolean isDependencyChild() {
		// TODO Auto-generated method stub
		return super.isDependencyChild();
	}

	@Override
	public boolean isDependencyParent() {
		// TODO Auto-generated method stub
		return super.isDependencyParent();
	}

	@Override
	public boolean isDOMAttribute() {
		// TODO Auto-generated method stub
		return super.isDOMAttribute();
	}

	@Override
	public boolean isDOMTextValue() {
		// TODO Auto-generated method stub
		return super.isDOMTextValue();
	}

	@Override
	public boolean isEmptyContentSignificant() {
		// TODO Auto-generated method stub
		return super.isEmptyContentSignificant();
	}

	@Override
	public boolean isEmptyTag() {
		// TODO Auto-generated method stub
		return super.isEmptyTag();
	}

	@Override
	public boolean isEnumFeature() {
		// TODO Auto-generated method stub
		return super.isEnumFeature();
	}

	@Override
	public boolean isEnumWithHyphens() {
		// TODO Auto-generated method stub
		return super.isEnumWithHyphens();
	}

	@Override
	public boolean isIDMap() {
		// TODO Auto-generated method stub
		return super.isIDMap();
	}

	@Override
	public boolean isLinkMap() {
		// TODO Auto-generated method stub
		return super.isLinkMap();
	}

	@Override
	public boolean isMapFor(Object aFeature, Object oldValue, Object newValue) {
		// TODO Auto-generated method stub
		return super.isMapFor(aFeature, oldValue, newValue);
	}

	@Override
	public boolean isMapFor(String domName) {
		// TODO Auto-generated method stub
		return super.isMapFor(domName);
	}

	@Override
	public boolean isMultiValued() {
		// TODO Auto-generated method stub
		return super.isMultiValued();
	}

	@Override
	public boolean isObjectMap() {
		// TODO Auto-generated method stub
		return super.isObjectMap();
	}

	@Override
	public boolean isSetMOFValue(EObject emfObject) {
		// TODO Auto-generated method stub
		return super.isSetMOFValue(emfObject);
	}

	@Override
	public boolean isShared() {
		// TODO Auto-generated method stub
		return super.isShared();
	}

	@Override
	public boolean isTargetLinkMap() {
		// TODO Auto-generated method stub
		return super.isTargetLinkMap();
	}

	@Override
	public boolean isUnsettable() {
		// TODO Auto-generated method stub
		return super.isUnsettable();
	}

	@Override
	protected String[] parseDOMNames(String domNamesString) {
		// TODO Auto-generated method stub
		return super.parseDOMNames(domNamesString);
	}

	@Override
	public void removeMOFValue(Notifier owner, Object value) {
		// TODO Auto-generated method stub
		super.removeMOFValue(owner, value);
	}

	@Override
	protected void setEMFClass(EClass anEClass) {
		// TODO Auto-generated method stub
		super.setEMFClass(anEClass);
	}

	@Override
	protected void setFeature(EStructuralFeature aFeature) {
		// TODO Auto-generated method stub
		super.setFeature(aFeature);
	}

	@Override
	public void setMOFValue(EObject emfObject, Object value) {
		// TODO Auto-generated method stub
		super.setMOFValue(emfObject, value);
	}

	@Override
	public void setMOFValue(Notifier owner, Object value, int newIndex) {
		// TODO Auto-generated method stub
		super.setMOFValue(owner, value, newIndex);
	}

	@Override
	public void setMOFValue(Notifier owner, Object value) {
		// TODO Auto-generated method stub
		super.setMOFValue(owner, value);
	}

	@Override
	public void setMOFValue(Resource res, Object value) {
		// TODO Auto-generated method stub
		super.setMOFValue(res, value);
	}

	@Override
	public void setMOFValueFromEmptyDOMPath(EObject eObject) {
		// TODO Auto-generated method stub
		super.setMOFValueFromEmptyDOMPath(eObject);
	}

	@Override
	public void setNameSpace(String string) {
		// TODO Auto-generated method stub
		super.setNameSpace(string);
	}

	@Override
	public void setTextValueIfNecessary(String textValue, Notifier owner, int versionId) {
		// TODO Auto-generated method stub
		super.setTextValueIfNecessary(textValue, owner, versionId);
	}

	@Override
	public boolean shouldIndentEndTag() {
		// TODO Auto-generated method stub
		return super.shouldIndentEndTag();
	}

	@Override
	public boolean shouldRenderEmptyDOMPath(EObject eObject) {
		// TODO Auto-generated method stub
		return super.shouldRenderEmptyDOMPath(eObject);
	}

	@Override
	public void unSetMOFValue(EObject emfObject) {
		// TODO Auto-generated method stub
		super.unSetMOFValue(emfObject);
	}
}
