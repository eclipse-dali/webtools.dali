package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util.URLEscaper;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

@SuppressWarnings("restriction")
public class JPACreateFactory {
	
	public static final String JPA_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.jpa.jar";
	public static final String ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.eclipselink.jar";	
	
	private static JPACreateFactory factory = null;
	private IFacetedProject facetedProject; 
	private IProject project; 
	private IJavaProject javaProject;
	//private IPackageFragmentRoot sourceFolder;
	JpaProject jpaProject;
	
	public static synchronized JPACreateFactory instance() {
		if (factory == null)
			factory = new JPACreateFactory();
		return factory;
	}
	
	private IProject buildPlatformProject(String projectName) throws CoreException {
		IWorkspaceDescription description = ResourcesPlugin.getWorkspace().getDescription();
		description.setAutoBuilding(true);
		ResourcesPlugin.getWorkspace().setDescription(description);		
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		p.create(null);
		p.open(null);
		return p;
	}

	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetInstallDataModelProvider());		
		dataModel.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, "1.0");
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM, null /*GenericPlatform.VERSION_1_0.getId()*/);
		dataModel.setProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}
	
	public JpaProject createJPAProject(String projectName) throws CoreException {
		return createJPAProject(projectName, null);
	}

	
	
	public JpaProject createJPAProject(String projectName, IDataModel jpaConfig) throws CoreException {
		project = buildPlatformProject(projectName);
		javaProject = createJavaProject(project, true);
		String jpaFacetVersion = "1.0";
		if (jpaConfig != null) {
			jpaFacetVersion = jpaConfig.getStringProperty(IFacetDataModelProperties.FACET_VERSION_STR);
		}
		installFacet(facetedProject, "jst.utility", "1.0");
		installFacet(facetedProject, "jpt.jpa", jpaFacetVersion, jpaConfig);
		addJar(javaProject, jpaJarName());
		if (eclipseLinkJarName() != null) {
			addJar(javaProject, eclipseLinkJarName());
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		jpaProject = JptJpaCorePlugin.getJpaProject(project);
		int cnt = 0;
		while ((jpaProject == null) && (cnt < 1000)){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			jpaProject = JptJpaCorePlugin.getJpaProject(project);
			cnt++;
		}
		jpaProject.setDiscoversAnnotatedClasses(true);
//		jpaProject.setUpdater(new SynchronousJpaProjectUpdater(jpaProject));
		return jpaProject;
	}
	
	public static String eclipseLinkJarName() {
		return getSystemProperty(ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY);
	}
	
	public void installFacet(IFacetedProject facetedProject,
							 String facetName, 
							 String versionName) throws CoreException {
		installFacet(facetedProject, facetName, versionName, null);
	}

	public void uninstallFacet(IFacetedProject facetedProject,
							   String facetName, 
							   String versionName) throws CoreException {
		uninstallFacet(facetedProject, facetName, versionName, null);
	}

	/**
	 * if 'config' is null (and 'facetName' is "jpt.jpa"), the JPA project will be built with the defaults
	 * defined in JpaFacetInstallDataModelProvider#getDefaultProperty(String)
	 */
	public void installFacet(IFacetedProject facetedProject,
							 String facetName, 
							 String versionName, 
							 IDataModel config) throws CoreException {
		facetedProject.installProjectFacet(this.facetVersion(facetName, versionName), config, null);
	}

	public void uninstallFacet(IFacetedProject facetedProject,
							   String facetName, 
							   String versionName, 
							   IDataModel config) throws CoreException {
		facetedProject.uninstallProjectFacet(this.facetVersion(facetName, versionName), config, null);
	}

	private IProjectFacetVersion facetVersion(String facetName, String versionName) {
		return ProjectFacetsManager.getProjectFacet(facetName).getVersion(versionName);
	}
	
	private static String getSystemProperty(String propertyName) {
		return System.getProperty(propertyName);
	}

	public void addJar(IJavaProject javaProject, String jarPath) throws JavaModelException {
		this.addClasspathEntry(javaProject, JavaCore.newLibraryEntry(new Path(jarPath), null, null));
	}

	private void addClasspathEntry(IJavaProject javaProject, IClasspathEntry entry) throws JavaModelException {
		javaProject.setRawClasspath(ArrayTools.add(javaProject.getRawClasspath(), entry), null);
	}
	
	private IFacetedProject createFacetedProject(IProject project) throws CoreException {
		return ProjectFacetsManager.create(project, true, null);		// true = "convert if necessary"
	}
	
	public IJavaProject createJavaProject(IProject project,  
										  @SuppressWarnings("unused") boolean autoBuild) throws CoreException {
		facetedProject = createFacetedProject(project);
		installFacet(facetedProject, "jst.java", "5.0");
		javaProject = JavaCore.create(project);
		//sourceFolder = javaProject.getPackageFragmentRoot(project.getFolder("src"));
		return javaProject;
	}
	
	public static String jpaJarName() {
		File file = null;
		try {
			URL bundleRoot =  JPAEditorTestsActivator.getDefault().getBundle().getEntry("/resources/javax.persistence_1.0.0.v200905011740.jar");   
			URL fileURL = FileLocator.toFileURL(bundleRoot);
			fileURL = new URL(URLEscaper.escape(fileURL.toString()));
			file = new File(fileURL.toURI());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
	
	public IFile createEntity(JpaProject jpaProject, String fullyQualifiedName) throws Exception {
		StringTokenizer tokenizer = new StringTokenizer(fullyQualifiedName, ".");
		ArrayList<String> nameElements = new ArrayList<String>();
		while(tokenizer.hasMoreElements()){
			nameElements.add(tokenizer.nextToken());
		}
		ArrayList<String> packageFragments = new ArrayList<String>();
		for(int i=0;i<nameElements.size()-1;i++){
			packageFragments.add(nameElements.get(i));
		}
		String[] packageStrings = new String[packageFragments.size()];
		for(int i=0;i<packageFragments.size();i++){
			packageStrings[i] = packageFragments.get(i);
		}
		String name = nameElements.get(Math.max(0, nameElements.size()-1));
//		SynchronousJpaProjectUpdater updater = new SynchronousJpaProjectUpdater(jpaProject);
//		updater.start();
		JpaRootContextNode jpaProjectContent = jpaProject.getRootContextNode();
		PersistenceXml persXML = jpaProjectContent.getPersistenceXml();
		int cnt = 0;
		while ((persXML == null) && (cnt < 100)) {		
			Thread.sleep(250);
			persXML = jpaProjectContent.getPersistenceXml();
			cnt++;
		}
		if (persXML == null)
			throw new NullPointerException("The persistence XML is not created");
		
		IFile entity1 = createEntityInProject(jpaProject.getProject(), packageStrings, name);
		JavaResourcePersistentType jrpt = jpaProject.getJavaResourcePersistentType(fullyQualifiedName);
		cnt = 0;
		while((jrpt == null) && (cnt < 100)) {
			try {
				Thread.sleep(250);
			} catch (Exception e) {} 
			jrpt = jpaProject.getJavaResourcePersistentType(fullyQualifiedName);
			cnt++;
		}
		if (jrpt == null)
			throw new NullPointerException("The entity '" + fullyQualifiedName + "' could not be created");		
		return entity1;
	}
	
	@SuppressWarnings("deprecation")
	public IFile createEntityInProject(IProject project, 
									   String[] packageFragments, 
									   String entityName) throws IOException, 
									   							 CoreException, 
									   							 JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);		
		IPackageFragmentRoot[] packageFragmentRoots = new IPackageFragmentRoot[0];
		final IClasspathEntry[] classpathEntries =  javaProject.getRawClasspath();		
		for (IClasspathEntry classpathEntry : classpathEntries) {
			if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				packageFragmentRoots = javaProject.getPackageFragmentRoots(classpathEntry);
				break;
			}  			
		}
		
		String folderName = packageFragmentRoots[0].getResource().getName();
		for (String fragment : packageFragments) {
			folderName += Path.SEPARATOR + fragment;
		}
		String packageName = packageFragments[0];		
		for (int i = 1; i < packageFragments.length; i++) {
			packageName += "." + packageFragments[i];
		}
		
		IPath path = new Path(folderName);
		IFolder folder = project.getFolder(path);		 
		return createEntity(folder, packageName , entityName);
	}
	
	private IFile createEntity(IFolder folder, String packageName, String entityName) throws IOException, CoreException {
		String entityShortName = entityName.substring(entityName.lastIndexOf('.') + 1);
		if (!folder.exists()) {
			createDirectories(folder, true, true, new NullProgressMonitor());
		}
		IFile file = folder.getFile(entityShortName + ".java");		
		if (!file.exists()) {
			String content = "package " + packageName + ";\n\n" 
					+ "import javax.persistence.*;\n\n" 
					+ "@Entity \n"
					+ "public class " + entityShortName + " {\n"
					+ "	private int id;\n"
					+ "	@Id \n"
					+ "	public int getId() {\n" 
					+ "		return id;\n"
					+ "	}\n"
					+ "	public void setId(int id) {\n"
					+ "		this.id = id;\n" 
					+ "	}\n"
					+ "}"; //$NON-NLS-1$
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			try {
				stream.write(content.getBytes());
				stream.flush();
				file.create(new ByteArrayInputStream(stream.toByteArray()), true, new NullProgressMonitor());
			} finally {
				stream.close();
			}	
		}
		return file;
	}	
	
	private void createDirectories(IContainer container, boolean force,
			boolean local, IProgressMonitor monitor) throws CoreException {
		if (container != null && container instanceof IFolder) {
			IFolder folder = (IFolder) container;
			if (!folder.exists()) {
				// depth first
				IContainer parent = folder.getParent();
				createDirectories(parent, force, local, null);
				// own second
				folder.create(force, local, monitor);
			}
		}
	}
	
	public static ReadOnlyPersistentAttribute getPersistentAttribute(IFile entity, String attributeName){
		Set<ReadOnlyPersistentAttribute> result = getEntityFields(entity);
		for(ReadOnlyPersistentAttribute attribute : result){
			if(attributeName.equals(attribute.getName())){
				return attribute;
			}
		}
		return null;
	}
	
	public static PersistentType getPersistentType(IFile file){
		JpaFile jpaFile = JptJpaCorePlugin.getJpaFile(file);
		for (JpaStructureNode node : getRootNodes(jpaFile)) {
			PersistentType entity = (PersistentType) node;
			return entity;
		}
		return null;
	}

	private static Iterable<JpaStructureNode> getRootNodes(JpaFile jpaFile) {
		if(jpaFile == null){
			return EmptyIterable.instance();
		}
		return jpaFile.getRootStructureNodes();
	}
	
	public static Set<ReadOnlyPersistentAttribute> getEntityFields(IFile file){
		Set<ReadOnlyPersistentAttribute> result = new HashSet<ReadOnlyPersistentAttribute>();
		JpaFile jpaFile = JptJpaCorePlugin.getJpaFile(file);
		if(jpaFile == null){
			return result;
		}
		for (JpaStructureNode node : getRootNodes(jpaFile)) {
			PersistentType entity = (PersistentType) node;
			for (Iterator<ReadOnlyPersistentAttribute> k = entity.allAttributes(); k.hasNext(); ) {
				ReadOnlyPersistentAttribute attribute = k.next();
				result.add(attribute);
			}
		}
		return result;
	}
	
	public void addAttributes(IFile entity, String attName, String attType, String annotation, String attActName, boolean isCollection){
		JavaPersistentType javaPersistentType = (JavaPersistentType)getPersistentType(entity);
		int cnt = 0;
		while ((javaPersistentType == null) && (cnt < 100)) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {}
			javaPersistentType = (JavaPersistentType)getPersistentType(entity);
			cnt++;
		}
		if (javaPersistentType == null)
			throw new RuntimeException("The entity could not be created");
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(entity);
		JpaArtifactFactory.instance().addNewAttribute(javaPersistentType, compilationUnit, attName, attType, annotation, attActName, isCollection, null);
	}
	
	private IFile createFieldAnnotatedEntity(IFolder folder, String packageName, String entityName) throws IOException, CoreException {
		String entityShortName = entityName.substring(entityName.lastIndexOf('.') + 1);
		if (!folder.exists()) {
			createDirectories(folder, true, true, new NullProgressMonitor());
		}
		IFile file = folder.getFile(entityShortName + ".java");		
		if (!file.exists()) {
			String content = "package " + packageName + ";\n\n" 
					+ "import javax.persistence.*;\n\n" 
					+ "@Entity \n"
					+ "public class " + entityShortName + " {\n"
					+ "	@Id \n"					
					+ "	private int id;\n"
					+ "	public int getId() {\n" 
					+ "		return id;\n"
					+ "	}\n"
					+ "	public void setId(int id) {\n"
					+ "		this.id = id;\n" 
					+ "	}\n"
					+ "}"; //$NON-NLS-1$
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			try {
				stream.write(content.getBytes());
				stream.flush();
				file.create(new ByteArrayInputStream(stream.toByteArray()), true, new NullProgressMonitor());
			} finally {
				stream.close();
			}	
		}
		return file;
	}	

	@SuppressWarnings("deprecation")
	public IFile createFieldAnnotatedEntityInProject(IProject project, String[] packageFragments, String entityName) throws IOException, CoreException, JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);		
		IPackageFragmentRoot[] packageFragmentRoots = new IPackageFragmentRoot[0];
		final IClasspathEntry[] classpathEntries =  javaProject.getRawClasspath();		
		for (IClasspathEntry classpathEntry : classpathEntries) {
			if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				packageFragmentRoots = javaProject.getPackageFragmentRoots(classpathEntry);
				break;
			}  			
		}
		
		String folderName = packageFragmentRoots[0].getResource().getName();
		for (String fragment : packageFragments) {
			folderName += Path.SEPARATOR + fragment;
		}
		String packageName = packageFragments[0];		
		for (int i = 1; i < packageFragments.length; i++) {
			packageName += "." + packageFragments[i];
		}
		
		IPath path = new Path(folderName);
		IFolder folder = project.getFolder(path);		 
		return createFieldAnnotatedEntity(folder, packageName , entityName);
	}
	
	
}
