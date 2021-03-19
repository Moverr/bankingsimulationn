import com.nimbusds.jose._
import com.nimbusds.jose.crypto._
import com.nimbusds.jose.jwk._
import com.nimbusds.jose.jwk.gen._


// RSA signatures require a public and private RSA key pair,
// the public key must be made known to the JWS recipient to
// allow the signatures to be verified
val rsaJWK:RSAKey  = new RSAKeyGenerator(2048) .keyID("sectret") .generate();
val rsaPublicJWK:RSAKey = rsaJWK.toPublicJWK();

// Create RSA-signer with the private key
val signer:JWSSigner  = new RSASSASigner(rsaJWK);

// Prepare JWS object with simple string as payload
var jwsObject:JWSObject  = new JWSObject(
  new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
  new Payload("moverr@gmail.com:password"));

// Compute the RSA signature
jwsObject.sign(signer);

// To serialize to compact form, produces something like
// eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L
// mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd
// maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7
// -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A
val s:String = jwsObject.serialize()

// To parse the JWS and verify it, e.g. on client-side
jwsObject = JWSObject.parse(s);

val verifier:JWSVerifier  = new RSASSAVerifier(rsaPublicJWK);


assert(jwsObject.verify(verifier));

println( jwsObject.getPayload().toString());