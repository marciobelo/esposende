package esposende.entidade;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponsavelTest {

    public static final DocumentoDigital FOTO = mock(DocumentoDigital.class);
    public static final String EMAIL = "rodrigo@ist-rio.net";

    @Test
    public void testaResponsavelNomeValido() {
        Responsavel responsavel = new Responsavel("Rodrigo","123", FOTO, EMAIL);
        assertEquals("Rodrigo", responsavel.getNome());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testaResponsavelNomeNaoPodeSerNulo() {
        new Responsavel(null,"123",FOTO,EMAIL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testaResponsavelNomeNaoPodeSerVazio() {
        new Responsavel("     ","123",FOTO,EMAIL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarMatriculaInvalidaVazia() {
        new Responsavel("     ","   ",FOTO,EMAIL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarMatriculaInvalidaNula() {
        new Responsavel("     ",null,FOTO,EMAIL);
    }

    @Test(expected = NumberFormatException.class)
    public void testarMatriculaInvalidaNaoNumerica1() {
        new Responsavel("Rodrigo","ABC",FOTO,EMAIL);
    }

    @Test(expected = NumberFormatException.class)
    public void testarMatriculaInvalidaNaoNumerica2() {
        new Responsavel("Rodrigo","123 456 789",FOTO,EMAIL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testaResponsavelNomeMaiorQue60caracteres() {
        new Responsavel("1234567890123456789012345678901234567890123456789012345678901","123",FOTO,EMAIL);
    }

    @Test
	public void testaEqualsFalseOutraClasse(){
		Origem origem = new Origem("teste", "teste");
		Responsavel responsavel = new Responsavel("Teste", "00000",FOTO,EMAIL);
		assertFalse(responsavel.equals(origem));
	}
	
	@Test
	public void testaEqualsMesmaClasseFalse(){
		Responsavel outroNome = new Responsavel("Falso", "00000",FOTO,EMAIL);
		Responsavel outraMatricula = new Responsavel("Teste", "11111",FOTO,EMAIL);
		Responsavel responsavel = new Responsavel("Teste", "00000",FOTO,EMAIL);
		assertFalse(responsavel.equals(outroNome));
		assertFalse(responsavel.equals(outraMatricula));
	}
	
	@Test
	public void testaEqualsMesmaClasseTrue(){
		Responsavel outro = new Responsavel("Teste", "00000",FOTO,EMAIL);
		Responsavel responsavel = new Responsavel("Teste", "00000",FOTO,EMAIL);
		assertTrue(responsavel.equals(outro));
	}
}
