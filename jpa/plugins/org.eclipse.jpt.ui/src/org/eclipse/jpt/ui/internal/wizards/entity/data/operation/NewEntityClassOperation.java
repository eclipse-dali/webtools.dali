/***********************************************************************
 * Copyright (c) 2008, 2009 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *     Dimiter Dimitrov, d.dimitrov@sap.com - initial API and implementation
 ***********************************************************************/
package org.eclipse.jpt.ui.internal.wizards.entity.data.operation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.codegen.jet.JETEmitter;
import org.eclipse.emf.codegen.jet.JETException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.wizards.entity.AnnotatedEntityTemplate;
import org.eclipse.jpt.ui.internal.wizards.entity.EntityTemplate;
import org.eclipse.jpt.ui.internal.wizards.entity.EntityWizardMsg;
import org.eclipse.jpt.ui.internal.wizards.entity.IdClassTemplate;
import org.eclipse.jpt.ui.internal.wizards.entity.data.model.CreateEntityTemplateModel;
import org.eclipse.jst.common.internal.annotations.controller.AnnotationsController;
import org.eclipse.jst.common.internal.annotations.controller.AnnotationsControllerManager;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;
import org.eclipse.jst.j2ee.internal.project.WTPJETEmitter;
import org.eclipse.wst.common.componentcore.internal.operation.ArtifactEditProviderOperation;
import org.eclipse.wst.common.componentcore.internal.operation.IArtifactEditOperationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.enablement.nonui.WFTWrappedException;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;

/**
 * The NewEntityClassOperation is IDataModelOperation following the
 * IDataModel wizard and operation framework.
 * 
 * @see org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation
 * @see org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider
 * 
 * This operation is used to generate java classes for the new JPA entity. It uses 
 * EntityDataModelProvider to store the appropriate properties required to generate the new entity. 
 * @see org.eclipse.jpt.ui.internal.wizards.entity.data.modelEntityDataModelProvider
 * 
 * A WTPJetEmitter entity template is used to create the class with the entity template. 
 * @see org.eclipse.jst.j2ee.internal.project.WTPJETEmitter
 * @see org.eclipse.jpt.ui.internal.wizards.entity.data.model.CreateEntityTemplateModel
 * 
 * The use of this class is EXPERIMENTAL and is subject to substantial changes.
 */
public class NewEntityClassOperation extends AbstractDataModelOperation {

	private static final String DOT_JAVA = ".java"; //$NON-NLS-1$
	private static final String SEPARATOR = "/";//$NON-NLS-1$
	private static final String VERSION_STRING = "1.0";//$NON-NLS-1$
	private static final String FIELD = "FIELD";//$NON-NLS-1$
	private static final String PROPERTY = "PROPERTY";//$NON-NLS-1$	
	protected static final String WTP_CUSTOMIZATION_PLUGIN = "WTP_CUSTOMIZATION_PLUGIN"; //$NON-NLS-1$
	protected static final String ANNOTATED_ENTITY_TEMPLATE_FILE = "/templates/annotated_entity.javajet"; //$NON-NLS-1$	
	protected static final String ENTITY_TEMPLATE_FILE = "/templates/entity.javajet"; //$NON-NLS-1$
	protected static final String IDCLASS_TEMPLATE_FILE = "/templates/idClass.javajet"; //$NON-NLS-1$	
	protected static final String BUILDER_ID = "builderId"; //$NON-NLS-1$
	private static final String EMPTY_STRING = "";//$NON-NLS-1$
	private static final String SINGLE_TABLE = "SINGLE_TABLE";//$NON-NLS-1$
	
	/**
	 * Method name of template implementation classes. 
	 */
	protected static final String GENERATE_METHOD = "generate"; //$NON-NLS-1$

	/**
	 * This is the constructor which should be used when creating a NewEntityClassOperation. 
	 * An instance of the CreateEntityTemplateModel should be passed in. This does not accept
	 * null parameter. It will not return null.
	 * 
	 * @see ArtifactEditProviderOperation#ArtifactEditProviderOperation(IDataModel)
	 * @see CreateEntityTemplateModel
	 * 
	 * @param dataModel
	 * @return NewFilterClassOperation
	 */
	public NewEntityClassOperation(IDataModel dataModel) {
		super(dataModel);
	}

	/**
	 * The implementation of the execute method drives the running of the operation. 
	 * This implementation will create the java source folder, create the java package, and then 
	 * the entity (or mapped as superclass) and ID class files will be created using templates. 
	 * 
	 * @see org.eclipse.wst.common.frameworks.internal.operation.WTPOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
	 * @see NewEntityClassOperation#generateUsingTemplates(IProgressMonitor,
	 *      IPackageFragment)
	 * 
	 * @param monitor
	 * @throws CoreException
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	public IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// Create source folder if it does not exist
		createJavaSourceFolder();
		// Create java package if it does not exist
		IPackageFragment pack = createJavaPackage();
		// Generate filter class using templates
		try {
			generateUsingTemplates(monitor, pack);
		} catch (Exception e) {
			return WTPCommonPlugin.createErrorStatus(e.toString());
		}
		return OK_STATUS;
	}

	/**
	 * This method will return the java package as specified by the new java
	 * class data model. If the package does not exist, it will create the
	 * package. This method should not return null.
	 * 
	 * @see INewJavaClassDataModelProperties#JAVA_PACKAGE
	 * @see IPackageFragmentRoot#createPackageFragment(java.lang.String,
	 *      boolean, org.eclipse.core.runtime.IProgressMonitor)
	 * 
	 * @return IPackageFragment the java package
	 */
	protected final IPackageFragment createJavaPackage() {
		// Retrieve the package name from the java class data model
		String packageName = model.getStringProperty(INewJavaClassDataModelProperties.JAVA_PACKAGE);
		IPackageFragmentRoot packRoot = (IPackageFragmentRoot) model
				.getProperty(INewJavaClassDataModelProperties.JAVA_PACKAGE_FRAGMENT_ROOT);
		IPackageFragment pack = packRoot.getPackageFragment(packageName);
		// Handle default package
		if (pack == null) {
			pack = packRoot.getPackageFragment(""); //$NON-NLS-1$
		}		
		
		// Create the package fragment if it does not exist
		if (!pack.exists()) {
			String packName = pack.getElementName();
			try {
				pack = packRoot.createPackageFragment(packName, true, null);
			} catch (JavaModelException e) {
				JptUiPlugin.log(e);
			}
		}
		// Return the package
		return pack;
	}

	/**
	 * This implementation uses the creation of a CreateEntityTemplateModel and the WTPJETEmitter
	 * to create the java class with the annotated tags. This method accepts null for monitor, it does not accept null 
	 * for fragment. If annotations are not being used the tags will be omitted from the class.
	 * 
	 * @see CreateEntityTemplateModel
	 * @see NewEntityClassOperation#generateTemplateSource(CreateEntityTemplateModel,
	 *      IProgressMonitor)
	 * 
	 * @param monitor
	 * @param fragment
	 * @throws CoreException
	 * @throws WFTWrappedException
	 */
	protected void generateUsingTemplates(IProgressMonitor monitor, IPackageFragment fragment) throws WFTWrappedException, CoreException {
	    // Create the entity template model
	    CreateEntityTemplateModel tempModel = createTemplateModel();
        IProject project = getTargetProject();
        String entityClassSource = null;
        String idClassSource = null;
        // Generate the java source based on the entity template models
        try {
        	if (tempModel.isArtifactsAnnotated()) {
        		AnnotatedEntityTemplate tempImpl = AnnotatedEntityTemplate.create(null);
        		entityClassSource = generateTemplateSource(tempModel, ANNOTATED_ENTITY_TEMPLATE_FILE, tempImpl, monitor);
        	} else {
        		EntityTemplate tempImpl = EntityTemplate.create(null);
        		entityClassSource = generateTemplateSource(tempModel, ENTITY_TEMPLATE_FILE, tempImpl, monitor);
        	}
            if (tempModel.isCompositePK()) {
            	IdClassTemplate tempImpl = IdClassTemplate.create(null);
            	idClassSource = generateTemplateSource(tempModel, IDCLASS_TEMPLATE_FILE, tempImpl, monitor);
            }
        } catch (Exception e) {
            throw new WFTWrappedException(e);
        }
        if (fragment != null) {
            // Create the java file
            String javaFileName = tempModel.getClassName() + DOT_JAVA;
            ICompilationUnit cu = fragment.getCompilationUnit(javaFileName);
            // Add the compilation unit to the java file
            if (cu == null || !cu.exists()) {
                cu = fragment.createCompilationUnit(javaFileName, entityClassSource, true, monitor);
            }
            IFile aFile = (IFile) cu.getResource();
            // Let the annotations controller process the annotated resource
            if (tempModel.isArtifactsAnnotated()) {
            	AnnotationsController controller = AnnotationsControllerManager.INSTANCE.getAnnotationsController(project);
            	if (controller != null) {
            		controller.process(aFile);
            	}
            }
            //Create IdClass if the primary key is complex
            if (idClassSource != null) {
                String entityPKName = tempModel.getIdClassName() + DOT_JAVA;
                ICompilationUnit cu1 = fragment.getCompilationUnit(entityPKName);
                // Add the compilation unit to the java file
                if (cu1 == null || !cu1.exists()) {
                    cu1 = fragment.createCompilationUnit(entityPKName, idClassSource, true, monitor);
                }           	
            }            
        }
                       
        if (!tempModel.isArtifactsAnnotated()) {
        	if (tempModel.isNonEntitySuperclass()) { 
        		addMappedSuperclassToXML(tempModel, project).schedule();
        	} else {
        		addEntityToXML(tempModel, project).schedule();
        	}
        }
        if (tempModel.isArtifactsAnnotated() && !JptCorePlugin.discoverAnnotatedClasses(project)) {
        	registerClassInPersistenceXml(tempModel, project).schedule();
        }
	}

	/**
     * This method is intended for internal use only. This method will create an
     * instance of the CreateEntityTemplateModel model to be used in conjunction
     * with the WTPJETEmitter. This method will not return null.
     * 
     * @see CreateEntityTemplateModel
     * @see NewEntityClassOperation#generateUsingTemplates(IProgressMonitor,
     *      IPackageFragment)
     * 
     * @return CreateFilterTemplateModel
     */
    private CreateEntityTemplateModel createTemplateModel() {
        CreateEntityTemplateModel templateModel = new CreateEntityTemplateModel(model);
        return templateModel;
    }
    
    /**
     * This method is intended for internal use only. This will use the
     * WTPJETEmitter to create an annotated java file based on the passed template model. 
     * This method does not accept null parameters. It will not return null. 
     * If annotations are not used, it will use the non annotated template to omit the annotated tags.
     * 
     * @see NewEntityClassOperation#generateUsingTemplates(IProgressMonitor,
     *      IPackageFragment)
     * @see JETEmitter#generate(org.eclipse.core.runtime.IProgressMonitor,
     *      java.lang.Object[])
     * @see CreateEntityTemplateModel
     * 
     * @param templateModel
     * @param monitor
     * @param template_file 
     * @return String the source for the java file
     * @throws JETException
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException  
     */
    private String generateTemplateSource(CreateEntityTemplateModel templateModel, String templateFile, Object templateImpl, IProgressMonitor monitor) 
    		throws JETException, SecurityException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    	Preferences preferences = J2EEPlugin.getDefault().getPluginPreferences();
		boolean dynamicTranslation = preferences.getBoolean(J2EEPlugin.DYNAMIC_TRANSLATION_OF_JET_TEMPLATES_PREF_KEY);
		if (dynamicTranslation) {
	        URL templateURL = FileLocator.find(JptUiPlugin.instance().getBundle(), new Path(templateFile), null);
	        cleanUpOldEmitterProject();
	        WTPJETEmitter emitter = new WTPJETEmitter(templateURL.toString(), this.getClass().getClassLoader());
	        emitter.setIntelligentLinkingEnabled(true);
	        emitter.addVariable(WTP_CUSTOMIZATION_PLUGIN, JptUiPlugin.PLUGIN_ID);
	        return emitter.generate(monitor, new Object[] { templateModel });
		} else {
			Method method = templateImpl.getClass().getMethod(GENERATE_METHOD, new Class[] { Object.class });
			return (String) method.invoke(templateImpl, templateModel);
		}
    }
    
	/**
	 * This method is intended for internal use only. It will clean up the old emmiter project 
	 * in order to prevent generation issues 
	 */
	private void cleanUpOldEmitterProject() {
		IProject project = ProjectUtilities.getProject(WTPJETEmitter.PROJECT_NAME);
		if (project == null || !project.exists())
			return;
		try {
			IMarker[] markers = project.findMarkers(IJavaModelMarker.BUILDPATH_PROBLEM_MARKER, false, IResource.DEPTH_ZERO);
			for (int i = 0, l = markers.length; i < l; i++) {
				if (((Integer) markers[i].getAttribute(IMarker.SEVERITY)).intValue() == IMarker.SEVERITY_ERROR) {
					project.delete(true, new NullProgressMonitor());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will return the java source folder as specified in the java
	 * class data model. It will create the java source folder if it does not
	 * exist. This method may return null.
	 * 
	 * @see INewJavaClassDataModelProperties#SOURCE_FOLDER
	 * @see IFolder#create(boolean, boolean,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 * 
	 * @return IFolder the java source folder
	 */
	protected final IFolder createJavaSourceFolder() {
		// Get the source folder name from the data model
		String folderFullPath = model.getStringProperty(INewJavaClassDataModelProperties.SOURCE_FOLDER);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFolder folder = root.getFolder(new Path(folderFullPath));
		// If folder does not exist, create the folder with the specified path
		if (!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				JptUiPlugin.log(e);
			}
		}
		// Return the source folder
		return folder;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return doExecute(monitor, info);
	}
	
	public IProject getTargetProject() {
		String projectName = model.getStringProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME);
		return ProjectUtilities.getProject(projectName);
	}	
		
	/**
	 * Adds entity to ORM XML in separate job
	 * @param model entity data model
	 * @param project JPA project in which the entity will be created
	 * @return
	 */
	private Job addEntityToXML(final CreateEntityTemplateModel model, final IProject project) {
		Job job = new Job(EntityWizardMsg.ADD_ENTITY_TO_XML) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final JpaXmlResource xmlResource = getOrmXmlResource(model, project);
				EntityMappings entityMappings = (EntityMappings) JptCorePlugin.getJpaProject(project).getJpaFile(xmlResource.getFile()).rootStructureNodes().next();
				OrmPersistentType persistentType = entityMappings.addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, model.getQualifiedJavaClassName());
				Entity entity = (Entity) persistentType.getMapping();
				if (model.isInheritanceSet()) {
					entity.setSpecifiedInheritanceStrategy(getModelInheritanceType(model));
				}
				
				if (model.isEntityNameSet()) {
					entity.setSpecifiedName(model.getEntityName());
				}
				if (model.isTableNameSet()) {
					entity.getTable().setSpecifiedName(model.getTableName());
				}
				if (model.isCompositePK()) {
					entity.getIdClassReference().setIdClassName(model.getIdClassName());
				}
				for (String fieldName : model.getPKFields()) {
					persistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, fieldName);
				}

				persistentType.setSpecifiedAccess(getModelAccessType(model));
				
				try {
					xmlResource.saveIfNecessary();
				}
				catch (Exception e) {
					JptUiPlugin.log(e);
				}
				return Status.OK_STATUS;
			}
		};
		return job;
	}

	protected JpaXmlResource getOrmXmlResource(CreateEntityTemplateModel model, IProject project) {
		if (model.isMappingXMLDefault()) {
			return JptCorePlugin.getJpaProject(project).getDefaultOrmXmlResource();
		}
		return JptCorePlugin.getJpaProject(project).getMappingFileXmlResource(model.getMappingXMLName());
	}
	
	/**
	 * Adds mapped superclass to ORM XML in separate job
	 * 
	 * @param model entity data model
	 * @param project JPA project in which the entity will be created
	 * @return the created job
	 */
	private Job addMappedSuperclassToXML(final CreateEntityTemplateModel model, final IProject project) {
		Job job = new Job(EntityWizardMsg.ADD_MAPPED_SUPERCLASS_TO_XML) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final JpaXmlResource xmlResource = getOrmXmlResource(model, project);
				EntityMappings entityMappings = (EntityMappings) JptCorePlugin.getJpaProject(project).getJpaFile(xmlResource.getFile()).rootStructureNodes().next();
				OrmPersistentType persistentType = entityMappings.addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, model.getQualifiedJavaClassName());
				MappedSuperclass mappedSuperclass = (MappedSuperclass) persistentType.getMapping();
				
				if (model.isCompositePK()) {
					mappedSuperclass.getIdClassReference().setIdClassName(model.getIdClassName());
				}
				
				for (String fieldName : model.getPKFields()) {
					persistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, fieldName);
				}

				persistentType.setSpecifiedAccess(getModelAccessType(model));
				
				try {
					xmlResource.saveIfNecessary();
				}
				catch (Exception e) {
					JptUiPlugin.log(e);
				}
				return Status.OK_STATUS;
			}
		};
		return job;		
	}
	
	protected AccessType getModelAccessType(CreateEntityTemplateModel model) {
		String accessTypeString = FIELD;
		if (!model.isFieldAccess()) {
			accessTypeString = PROPERTY;
		}
		return AccessType.fromOrmResourceModel(OrmFactory.eINSTANCE.createAccessTypeFromString(null, accessTypeString));// TODO
	}

	protected InheritanceType getModelInheritanceType(CreateEntityTemplateModel model) {
		String inheritanceStrategy = model.getInheritanceStrategyName();
		if (inheritanceStrategy.equals(EMPTY_STRING)) {
			inheritanceStrategy = SINGLE_TABLE;
		}
		return InheritanceType.fromOrmResourceModel(OrmFactory.eINSTANCE.createInheritanceTypeFromString(null, inheritanceStrategy));//TODO
	}

	/**
	 * Regist the class in the persistence.xml
	 * 
	 * @param model entity data model
	 * @param project JPA project in which the entity will be created
	 * @return the created job
	 */
	private Job registerClassInPersistenceXml(final CreateEntityTemplateModel model, final IProject project) {
		Job job = new Job(EntityWizardMsg.APPLY_CHANGES_TO_PERSISTENCE_XML) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
				final JpaXmlResource resource = jpaProject.getPersistenceXmlResource();
				resource.modify(new Runnable() {
						public void run() {
							XmlPersistence xmlPersistence = (XmlPersistence) resource.getRootObject();
							EList<XmlPersistenceUnit> persistenceUnits = xmlPersistence.getPersistenceUnits();
							XmlPersistenceUnit persistenceUnit = persistenceUnits.get(0);// Multiply persistence unit support
							
							if (!model.isNonEntitySuperclass()) {
								XmlJavaClassRef classRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
								classRef.setJavaClass(model.getQualifiedJavaClassName());
								persistenceUnit.getClasses().add(classRef);
							}
						}
					});
				
				return Status.OK_STATUS;
			}
		};
		return job;

	}
	
	/**
	 * @param input the name of mapping XML from the class wizard page. It is relative path from the source folder
	 * and includes META-INF folder
	 * @return the simple name of the mapping XML
	 */
	private String getLastSegment(String input) {
		String output = input;
		if (input.indexOf(SEPARATOR) != -1) {
			output = input.substring(input.lastIndexOf(SEPARATOR) + 1);
		}
		return output;
	}
	
}
