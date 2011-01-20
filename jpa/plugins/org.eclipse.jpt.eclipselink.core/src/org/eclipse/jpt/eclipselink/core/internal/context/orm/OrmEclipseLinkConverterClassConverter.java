package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class OrmEclipseLinkConverterClassConverter<X extends XmlNamedConverter>  extends OrmEclipseLinkConverter<X>
	implements EclipseLinkCustomConverter 
{
	
	protected String converterClass;

	protected JavaResourcePersistentType converterPersistentType;
	
	public OrmEclipseLinkConverterClassConverter(XmlContextNode parent, X xmlConverter) {
		super(parent, xmlConverter);
	}
	
	// **************** converter class ****************************************
	
	public String getConverterClass() {
		return this.converterClass;
	}
	
	protected void setConverterClass_(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}

	protected JavaResourcePersistentType getConverterJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.converterClass);
	}

	// **************** resource interaction ***********************************
	
	protected void updateConverterPersistentType() {
		this.converterPersistentType = this.getConverterJavaResourcePersistentType();
	}
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validateConverterClass(messages);
	}
	
	protected void validateConverterClass(List<IMessage> messages) {
		IJavaProject javaProject = getJpaProject().getJavaProject();
		
		if (StringTools.stringIsEmpty(this.converterClass)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_DEFINED,
					this,
					getConverterClassTextRange()
				)
			);			
		}
		else if (!converterClassExists(javaProject)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_EXISTS,
					new String[] {this.converterClass},
					this,
					getConverterClassTextRange()
				)
			);			
		} 
		else if (!converterClassImplementsInterface(javaProject, ECLIPSELINK_CONVERTER_CLASS_NAME)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_IMPLEMENTS_CONVERTER,
					new String[] {this.converterClass},
					this,
					getConverterClassTextRange()
				)
			);			
		}
	}
	
	private boolean converterClassExists(IJavaProject javaProject) {
		if (this.converterClass != null) 
		{
			String globalPackage = getEntityMappings().getPackage();
			IType type = JDTTools.getJDTType(javaProject, this.converterClass);
			if (type != null) {
				return true;
			} else if (StringTools.stringIsNotEmpty(globalPackage)) {
				type = JDTTools.getJDTType(javaProject, globalPackage + "." + this.converterClass);
				if (type != null) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean converterClassImplementsInterface(IJavaProject javaProject, String interfaceName) {
		if (this.converterClass != null) 
		{
			String globalPackage = getEntityMappings().getPackage();
			boolean implementFound = JDTTools.typeNamedImplementsInterfaceNamed(javaProject, this.converterClass, interfaceName);
			if (!implementFound && StringTools.stringIsNotEmpty(globalPackage)) {
				implementFound = JDTTools.typeNamedImplementsInterfaceNamed(javaProject, globalPackage + "." + this.converterClass, interfaceName);
			}
			return implementFound;
		}
		return false;
	}
	
	public abstract TextRange getConverterClassTextRange();
	
	//************************* refactoring ************************

	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected abstract ReplaceEdit createRenameEdit(IType originalType, String newName);

	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newPackage.getElementName())) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected abstract ReplaceEdit createRenamePackageEdit(String newName);
	
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.isIn(originalPackage) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}
	
	protected boolean isFor(String typeName) {
		JavaResourcePersistentType converterType = this.getConverterJavaResourcePersistentType();
		return (converterType != null) && converterType.getQualifiedName().equals(typeName);
	}

	protected boolean isIn(IPackageFragment packageFragment) {
		JavaResourcePersistentType converterType = this.getConverterJavaResourcePersistentType();
		return (converterType != null) && converterType.isIn(packageFragment);
	}
	
}
