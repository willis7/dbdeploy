package com.dbdeploy;

import com.dbdeploy.exceptions.UsageException;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class DbDeployTest {
	private final DbDeploy dbDeploy = new DbDeploy();

	@Before
	public void setSensibleDefaultValuesForAllParameters() {
		dbDeploy.setDriver(getClass().getName());

		dbDeploy.setUserid("someUser");

		dbDeploy.setDbms("hsql");
		dbDeploy.setUrl("jdbc:hsqldb:mem:dbdeploy");

		dbDeploy.setScriptdirectory(new File("."));
		dbDeploy.setOutputfile(new File("a.txt"));
	}

	@Test(expected = ClassNotFoundException.class)
	public void shouldThrowIfInvalidDriverClassNameSpecified() throws Exception {
		dbDeploy.setDriver("some.class.that.will.not.be.Found");
		dbDeploy.go();
	}

	@Test(expected = UsageException.class)
	public void shouldThrowIfUserIdNotSpecified() throws Exception {
		dbDeploy.setUserid(null);
		dbDeploy.go();
	}

	@Test(expected = UsageException.class)
	public void shouldThrowIfDriverNotSpecified() throws Exception {
		dbDeploy.setDriver(null);
		dbDeploy.go();
	}

	@Test(expected = UsageException.class)
	public void shouldThrowIfUrlNotSpecified() throws Exception {
		dbDeploy.setUrl(null);
		dbDeploy.go();
	}

	@Test
	public void shouldThrowIfScriptDirectoryIsNotAValidDirectory() throws Exception {
		dbDeploy.setScriptdirectory(new File("fileThatDoesntExist.txt"));
		try {
			dbDeploy.go();
			fail("exception expected");
		} catch (UsageException e) {
			assertEquals("Script directory must point to a valid directory", e.getMessage());
		}
	}

	@Test
	public void shouldReportVersionNumberWithoutCrashing() {
		assertThat(dbDeploy.getWelcomeString(), startsWith("dbdeploy"));
	}


}
