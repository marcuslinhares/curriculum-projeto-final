package dev.marcus.curriculum.config.setup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.marcus.curriculum.models.entities.RegraEntity;
import dev.marcus.curriculum.models.entities.UsuarioEntity;
import dev.marcus.curriculum.models.enums.RegraNomeEnum;
import dev.marcus.curriculum.repositories.RegraRepository;
import dev.marcus.curriculum.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminCandidatoSetupRunner implements CommandLineRunner{
    private final RegraRepository regraRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${config.setup.admin.nome}")
    private String adminNome;
    @Value("${config.setup.admin.email}")
    private String adminEmail;
    @Value("${config.setup.admin.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) throws Exception {
        
        if(this.regraRepository.findByNome(RegraNomeEnum.ADMIN).isEmpty()) {
            regraRepository.save(RegraEntity.builder().nome(RegraNomeEnum.ADMIN).build());
        }

        if(this.regraRepository.findByNome(RegraNomeEnum.CANDIDATO).isEmpty()) {
            regraRepository.save(RegraEntity.builder().nome(RegraNomeEnum.CANDIDATO).build());
        }

        if(this.usuarioRepository.findByEmail(this.adminEmail).isEmpty()) {
            usuarioRepository.save(
                UsuarioEntity.builder()
                    .nome(this.adminNome)
                    .email(this.adminEmail)
                    .senha(this.passwordEncoder.encode(
                        this.adminSenha
                    ))
                    .regra(this.regraRepository.findByNome(RegraNomeEnum.ADMIN).get())
                .build()
            );
        }
    }

}
