package bot.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import bot.entity.Userbot;

public interface UserbotRepository extends JpaRepository<Userbot, Integer> {

	Userbot findByIdT(Integer idT);
}
