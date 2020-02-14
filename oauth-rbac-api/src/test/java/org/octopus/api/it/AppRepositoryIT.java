package org.octopus.api.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.api.entity.AppEntity;
import org.octopus.api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AppRepositoryIT {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AppRepository appRepository;

	@Before
	public void setUp() {
		// given
		AppEntity entity = AppEntity//
				.builder()//
				.name("MyApp")//
				.shortName("MyShortApp")//
				.appId("123")//
				.contact("abc@gmail.com")//
				.build();
		entityManager.persist(entity);
		entityManager.flush();
	}

	@Test
	public void test_findAll() {
		// given

		// when
		List<AppEntity> apps = appRepository.findAll();

		// then
		assertEquals(1, apps.size());
	}

	@Test
	public void test_findByName() {
		// given

		// when
		List<AppEntity> apps = appRepository.findByName("MyApp");
		assertEquals(1, apps.size());

		// then
		assertThat(apps).extracting(AppEntity::getName).containsOnly("MyApp");
	}

	@Test
	public void test_findByShortName() {

		List<AppEntity> apps = appRepository.findByShortName("MyShortApp");
		assertEquals(1, apps.size());

		assertThat(apps).extracting(AppEntity::getShortName).containsOnly("MyShortApp");
	}

	@Test
	public void test_findByAppId() {

		List<AppEntity> apps = appRepository.findByAppId("123");
		assertEquals(1, apps.size());

		assertThat(apps).extracting(AppEntity::getAppId).containsOnly("123");
	}

	@Test
	public void test_findByContact() {

		List<AppEntity> apps = appRepository.findByContact("%gmail%");
		assertEquals(1, apps.size());

		assertThat(apps).extracting(AppEntity::getContact).contains("abc@gmail.com");
	}
}
