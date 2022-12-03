package com.dbc.vemvemser.security;




import com.dbc.vemvemser.dto.CargoDto;
import com.dbc.vemvemser.dto.TokenDto;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.enums.TipoEmail;
import com.dbc.vemvemser.service.CargoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String CHAVE_CARGOS = "CARGOS";
    private final ObjectMapper objectMapper;

    private static final int VALIDADE_TOKEN_CINCO_MINUTOS = 20;
    private static final int VALIDADE_TOKEN_UM_DIA = 1;
    @Value("${jwt.secret}")
    private String secret;

    public TokenDto getToken(GestorEntity gestorEntity, Boolean recuperacao) {

        LocalDateTime dataLocalDateTime = LocalDateTime.now();
        Date date = Date.from(dataLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Date dateExpiration;
        if(recuperacao){
            LocalDateTime localDateExperation = dataLocalDateTime.plusMinutes(VALIDADE_TOKEN_CINCO_MINUTOS);
            dateExpiration = Date.from(localDateExperation.atZone(ZoneId.systemDefault()).toInstant());
        }else {
            LocalDateTime localDateExperation = dataLocalDateTime.plusDays(VALIDADE_TOKEN_UM_DIA);
            dateExpiration = Date.from(localDateExperation.atZone(ZoneId.systemDefault()).toInstant());
        }
        List<String> cargosDoGestor = gestorEntity.getAuthorities().stream()
                .map(gestorEntity1 -> gestorEntity1.getAuthority())
                .toList();

        String token = Jwts.builder()
                .setIssuer("vemvemser")
                .claim(Claims.ID, gestorEntity.getIdGestor().toString())
                .claim(CHAVE_CARGOS,cargosDoGestor)
                .setIssuedAt(date)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return new TokenDto(token,gestorEntity.getIdGestor(),objectMapper.convertValue(gestorEntity.getCargoEntity(), CargoDto.class));
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if (token == null) {
            return null;
        }

        token = token.replace("Bearer ", "");

        Claims chaves = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String idUsuario = chaves.get(Claims.ID, String.class);

        List<String> cargos = chaves.get(CHAVE_CARGOS, List.class);

        List<SimpleGrantedAuthority> cargosList = cargos.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();


        return new UsernamePasswordAuthenticationToken(idUsuario,
                null, cargosList);
    }
}
