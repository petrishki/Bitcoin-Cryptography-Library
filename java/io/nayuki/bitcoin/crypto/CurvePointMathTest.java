/* 
 * Bitcoin cryptography library
 * Copyright (c) Project Nayuki
 * 
 * http://www.nayuki.io/page/bitcoin-cryptography-library
 * https://github.com/nayuki/Bitcoin-Cryptography-Library
 */

package io.nayuki.bitcoin.crypto;

import static org.junit.Assert.assertArrayEquals;
import java.util.Arrays;
import java.util.Random;
import org.junit.Test;


/**
 * Tests the functions of the CurvePointMath class.
 */
public final class CurvePointMathTest {
	
	@Test public void testTwice() {
		String[][] cases = {
			{"c6047f9441ed7d6d3045406e95c07cd85c778e4b8cef3ca7abac09b95c709ee5", "1ae168fea63dc339a3c58419466ceaeef7f632653266d0e1236431a950cfe52a"},
			{"e493dbf1c10d80f3581e4904930b1404cc6c13900ee0758474fa94abe8c4cd13", "51ed993ea0d455b75642e2098ea51448d967ae33bfbdfe40cfe97bdc47739922"},
			{"2f01e5e15cca351daff3843fb70f3c2f0a1bdd05e5af888a67784ef3e10a2a01", "5c4da8a741539949293d082a132d13b4c2e213d6ba5b7617b5da2cb76cbde904"},
			{"e60fce93b59e9ec53011aabc21c23e97b2a31369b87a5ae9c44ee89e2a6dec0a", "f7e3507399e595929db99f34f57937101296891e44d23f0be1f32cce69616821"},
			{"d30199d74fb5a22d47b6e054e2f378cedacffcb89904a61d75d0dbd407143e65", "95038d9d0ae3d5c3b3d6dec9e98380651f760cc364ed819605b3ff1f24106ab9"},
			{"bf23c1542d16eab70b1051eaf832823cfc4c6f1dcdbafd81e37918e6f874ef8b", "5cb3866fc33003737ad928a0ba5392e4c522fc54811e2f784dc37efe66831d9f"},
			{"34ff3be4033f7a06696c3d09f7d1671cbcf55cd700535655647077456769a24e", "5d9d11623a236c553f6619d89832098c55df16c3e8f8b6818491067a73cc2f1a"},
			{"8282263212c609d9ea2a6e3e172de238d8c39cabd5ac1ca10646e23fd5f51508", "11f8a8098557dfe45e8256e830b60ace62d613ac2f7b17bed31b6eaff6e26caf"},
			{"465370b287a79ff3905a857a9cf918d50adbc968d9e159d0926e2c00ef34a24d", "35e531b38368c082a4af8bdafdeec2c1588e09b215d37a10a2f8fb20b33887f4"},
			{"241febb8e23cbd77d664a18f66ad6240aaec6ecdc813b088d5b901b2e285131f", "513378d9ff94f8d3d6c420bd13981df8cd50fd0fbd0cb5afabb3e66f2750026d"},
		};
		int[] p = CurvePointMath.getBasePoint();
		for (String[] cs : cases) {
			int pOff = rand.nextInt(5) * 8;
			int tempOff = pOff + 24 + rand.nextInt(5) * 8;
			int[] arr = new int[tempOff + 72];
			for (int i = 0; i < arr.length; i++)
				arr[i] = rand.nextInt();
			System.arraycopy(p, 0, arr, pOff, 24);
			
			CurvePointMath.twice(arr, pOff, tempOff);
			CurvePointMath.normalize(arr, pOff, tempOff);
			assertArrayEquals(toInt256(cs[0]), Arrays.copyOfRange(arr, pOff + 0, pOff + 8));
			assertArrayEquals(toInt256(cs[1]), Arrays.copyOfRange(arr, pOff + 8, pOff + 16));
			assertArrayEquals(INT256_ONE, Arrays.copyOfRange(arr, pOff + 16, pOff + 24));
			System.arraycopy(arr, pOff, p, 0, 24);
		}
	}
	
	
	@Test public void testAdd() {
		String[][] cases = {
			{"d8e2f51f86ccb79f9774d7f56eeb6d65e012076726eedea4c9bedfc25c780af1", "8546cf18d7f20800376cb6d6c6a3042f39e4da8738ee9a81949deef77fe8cdfe", "f01dc6bb112fb5ffe8e77d173453db479a368794b12b4ac616154b454b7a99cc", "c6a200d4b689220345c67d2f8fd3c9a4789a7a0139efa829e783285e7b7099c7", "0da4159113da119c4d351fe5fa148433251b7868c80c68114fbd1addc660831f", "ea70b1a7fb90118f7491f47ae86596a5763d1be2e58a122e75f137f677b97c8b"},
			{"4f49d384bdbab681da3612e6f31a460445ec2cffeeddff2e1d6abe0bdb483702", "a6154dad136b01339e899f014d93e009e07acfbf258024b3b8c6135627984143", "7a47cf13069a4e516a73e8fc0362b77088e842542b4f5cbdaac77db39508561c", "47e98eadbf4be548f9db09b2e4a36de92049ffd5735651d5cdb794fb22a1a2a5", "0a10b520547be0572a326fe3d375069bcc47b84f80bc5f6155b41c42dd059e93", "6e448864523a61fdf536576c799c2db0f1782f7c1acd9d89e854e778d370a3a7"},
			{"3d2602f0f70649cd7e03254326f24decc79a7040a948ad5a21d97f3ae82a2af9", "15528860fda6a013614a58b7a80791aa6903cac1c0bfff13c0bfe6d80040b7dc", "fc6192342b488f5c1cc356f5b16e68c8c843375dfeb6f1d5e60586a223958ab6", "5a9c457e4aa9b0622401b7b6d1aa8a78d100557e37f01839be4b008292fd19b8", "43fa9a65c8de9f4b5e0ccc959c8cf1cbf59d58287ab34f84d79fcacfbf5ae138", "ea5df38d09be2155d88cd7a10fec168d3281bebbf1b6fac1fb6a6e0ff7c61ece"},
			{"8cd590e2fa6e47ba831510a742e31c5967282cc5af705bb7ee05ddb1ae0a70cc", "3cc503de45487e796b4e87b7e7805450a2411d2162390f8bb2f37f7a8bc0a43f", "2650633f2c463ae2c423a8370d37c89f9415de52ed6e701c8208f0032d74be9d", "41d7e50961a0c46258ef2ebc62165686f68773ab8f0c430504a50c65535f6b0a", "e683b66a22e2fa01b4b9bd2fcc1136a2634a65a9f29803c8f86fb9e953169f0b", "f7ac842df72fe9e7872db5671797a1856f9dc161ee6c26f016b1d1ed3ddfe877"},
			{"d4f1595af9fcd0200007504053d531ebfaafa9ad8b824cd41fd3cbf3a6f89ccf", "7543303d5ad16221371deca33f883dba7e70382a5ebf2b22f5bfc64548f67e09", "19def82a4cac5a0d1c6a1328a3bf31abc4c4739c700033b89a6964cb57ed72dc", "1e98a224f7a980bfb0eb601ed34bd549d8f6e9b5c9893cc0142621f48fd12301", "7994c996e639207e9b2bac683aeed5f6554da0d4573df0c018201da32f43658d", "e5bd7828f71a1471f16139d1955dfc2877c829ce2c2b4da88f88cebcad32489d"},
			{"b4171048a82b057ecbb2410cb5cca894fefd753cb3dbf08f5ae7d1da53b32460", "a612c60587a7471941a690d294ef44e5009cdb2c88609da8e7a3f20851e061a4", "4e84c012316583837a181eed9f4b6b2c93180d59b8401ab9f000c1e794b63b56", "0a2248dd75a023b25710eb8d6f2202d2a8afddcb5a1d3167f24f32f989def523", "658cc032552673790961d1aa665bc5e6904e9c34b2bd188081f29ef01fa088b2", "2da31fe2272c8c35e1a7a8c89630d4690b0d2bc852b4b99fedbab0144575dc7b"},
			{"6bf99f31f70956a43ff30a3dfe7015a1bdea1c72d360bb0c2cbe9009e8eb8df8", "f40a505797bd58f6474b5aa95c1660f9d471196ceebb29944920325a71ee9ef7", "c2674e87cf2f148fa4cd5a5d7301c2f928d9324987b63150a6a23010c51179cd", "a35b2ab12251d0306f28e5a6bd68bbca88681cf318746579a64a8532830f4283", "56a652e288a1243e0f110a5d5b1e27ad47b4a2a2cc0e00ba9f07385b9ae2d26b", "45db788a74cb0a8f4ec82458b8008c0a3395d36851bacd07a2e346fed23d1455"},
			{"ec0cebc10e8a40a62a79d6d71c188efaefe627db850d8fc39ca98311ee9681e1", "b8a6d325f045e42750506bc316860df05270a0034a6a3df329aac51abd267e6b", "f05137fe20f64e3c0b134928a2697f1e7e530a4529cd6ddb4fa897bdb20cffc4", "070e580de65d8f984db6e7cc08cfba4b0a2684b0f1c30cb89b22aee8db81d4f4", "42b4614b78e06a98af4f459c74e75debb462123ffd3c623060a10a245765597b", "e6c6c5bcf997a5717a01031aede414987625475cec47e9e171f0c959e0a5d123"},
			{"a14224fc7c828f17f47cf41e993904837f146f4ceb3a580fc2d1c904c11c230b", "39088e64f90572c0a95ec6fe2169c95189e177a4e51e6a97156832177033dd28", "7de79e3064bac0e0c51d9cb0be4e29d7d92e901506292caabe373d7ca1602e1b", "2ac948dd8724036d0d7b3d3a61bcfde20afe50277f3f14bd03475617770b8ea9", "835937c7310e89ce70937d7e2df2567fa15b12525cccd13f266d2a5031b09111", "ba7fba3c8751c14f9a0e650cfaca9176bc1a592671aa1ce9547d3007632a1455"},
			{"c152768b4ed3f7e3f0d680838c172402185556ab704ff0b0aa862addad05824e", "cd5f61613c622615f1679d4ae82de6cd886e51989fcbcbc769913f24c3d5252a", "d896c04205cac67544c5176e8f257464ee19364a641442f0921a9e3c883237a5", "1bc74789d084c7c100c74466d9ff0dd5b195c57fb0a172ecdf9cae26168a7d1e", "ad6c4498e4e18a48a905910601472e975ec00cbc89dd8714f27bd730533653ce", "1009ad5abd73bcd7cc5c814ca9d2aee18e90fd4127c8ec30c7217f6e794a7932"},
		};
		for (String[] cs : cases) {
			int pOff = rand.nextInt(5) * 8;
			int qOff = pOff + 24 + rand.nextInt(5) * 8;
			int tempOff = qOff + 24 + rand.nextInt(5) * 8;
			int[] arr = new int[tempOff + 144];
			for (int i = 0; i < arr.length; i++)
				arr[i] = rand.nextInt();
			
			toInt256(cs[0], arr, pOff + 0);
			toInt256(cs[1], arr, pOff + 8);
			System.arraycopy(INT256_ONE, 0, arr, pOff + 16, 8);
			toInt256(cs[2], arr, qOff + 0);
			toInt256(cs[3], arr, qOff + 8);
			System.arraycopy(INT256_ONE, 0, arr, qOff + 16, 8);
			
			CurvePointMath.add(arr, pOff, qOff, tempOff);
			CurvePointMath.normalize(arr, pOff, tempOff);
			assertArrayEquals(toInt256(cs[4]), Arrays.copyOfRange(arr, pOff + 0, pOff + 8));
			assertArrayEquals(toInt256(cs[5]), Arrays.copyOfRange(arr, pOff + 8, pOff + 16));
			assertArrayEquals(INT256_ONE, Arrays.copyOfRange(arr, pOff + 16, pOff + 24));
		}
	}
	
	
	@Test public void testMultiply() {
		String[][] cases = {
			// Small multiples
			{"0000000000000000000000000000000000000000000000000000000000000000", null, null},
			{"0000000000000000000000000000000000000000000000000000000000000001", "79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798", "483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8"},
			{"0000000000000000000000000000000000000000000000000000000000000002", "c6047f9441ed7d6d3045406e95c07cd85c778e4b8cef3ca7abac09b95c709ee5", "1ae168fea63dc339a3c58419466ceaeef7f632653266d0e1236431a950cfe52a"},
			{"0000000000000000000000000000000000000000000000000000000000000003", "f9308a019258c31049344f85f89d5229b531c845836f99b08601f113bce036f9", "388f7b0f632de8140fe337e62a37f3566500a99934c2231b6cb9fd7584b8e672"},
			{"0000000000000000000000000000000000000000000000000000000000000004", "e493dbf1c10d80f3581e4904930b1404cc6c13900ee0758474fa94abe8c4cd13", "51ed993ea0d455b75642e2098ea51448d967ae33bfbdfe40cfe97bdc47739922"},
			{"0000000000000000000000000000000000000000000000000000000000000005", "2f8bde4d1a07209355b4a7250a5c5128e88b84bddc619ab7cba8d569b240efe4", "d8ac222636e5e3d6d4dba9dda6c9c426f788271bab0d6840dca87d3aa6ac62d6"},
			{"0000000000000000000000000000000000000000000000000000000000000006", "fff97bd5755eeea420453a14355235d382f6472f8568a18b2f057a1460297556", "ae12777aacfbb620f3be96017f45c560de80f0f6518fe4a03c870c36b075f297"},
			{"0000000000000000000000000000000000000000000000000000000000000007", "5cbdf0646e5db4eaa398f365f2ea7a0e3d419b7e0330e39ce92bddedcac4f9bc", "6aebca40ba255960a3178d6d861a54dba813d0b813fde7b5a5082628087264da"},
			{"0000000000000000000000000000000000000000000000000000000000000008", "2f01e5e15cca351daff3843fb70f3c2f0a1bdd05e5af888a67784ef3e10a2a01", "5c4da8a741539949293d082a132d13b4c2e213d6ba5b7617b5da2cb76cbde904"},
			{"0000000000000000000000000000000000000000000000000000000000000009", "acd484e2f0c7f65309ad178a9f559abde09796974c57e714c35f110dfc27ccbe", "cc338921b0a7d9fd64380971763b61e9add888a4375f8e0f05cc262ac64f9c37"},
			{"000000000000000000000000000000000000000000000000000000000000000a", "a0434d9e47f3c86235477c7b1ae6ae5d3442d49b1943c2b752a68e2a47e247c7", "893aba425419bc27a3b6c7e693a24c696f794c2ed877a1593cbee53b037368d7"},
			{"000000000000000000000000000000000000000000000000000000000000000b", "774ae7f858a9411e5ef4246b70c65aac5649980be5c17891bbec17895da008cb", "d984a032eb6b5e190243dd56d7b7b365372db1e2dff9d6a8301d74c9c953c61b"},
			{"000000000000000000000000000000000000000000000000000000000000000c", "d01115d548e7561b15c38f004d734633687cf4419620095bc5b0f47070afe85a", "a9f34ffdc815e0d7a8b64537e17bd81579238c5dd9a86d526b051b13f4062327"},
			{"000000000000000000000000000000000000000000000000000000000000000d", "f28773c2d975288bc7d1d205c3748651b075fbc6610e58cddeeddf8f19405aa8", "0ab0902e8d880a89758212eb65cdaf473a1a06da521fa91f29b5cb52db03ed81"},
			{"000000000000000000000000000000000000000000000000000000000000000e", "499fdf9e895e719cfd64e67f07d38e3226aa7b63678949e6e49b241a60e823e4", "cac2f6c4b54e855190f044e4a7b3d464464279c27a3f95bcc65f40d403a13f5b"},
			{"000000000000000000000000000000000000000000000000000000000000000f", "d7924d4f7d43ea965a465ae3095ff41131e5946f3c85f79e44adbcf8e27e080e", "581e2872a86c72a683842ec228cc6defea40af2bd896d3a5c504dc9ff6a26b58"},
			{"0000000000000000000000000000000000000000000000000000000000000010", "e60fce93b59e9ec53011aabc21c23e97b2a31369b87a5ae9c44ee89e2a6dec0a", "f7e3507399e595929db99f34f57937101296891e44d23f0be1f32cce69616821"},
			// Large multiples
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364139", "2f01e5e15cca351daff3843fb70f3c2f0a1bdd05e5af888a67784ef3e10a2a01", "a3b25758beac66b6d6c2f7d5ecd2ec4b3d1dec2945a489e84a25d3479342132b"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd036413a", "5cbdf0646e5db4eaa398f365f2ea7a0e3d419b7e0330e39ce92bddedcac4f9bc", "951435bf45daa69f5ce8729279e5ab2457ec2f47ec02184a5af7d9d6f78d9755"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd036413b", "fff97bd5755eeea420453a14355235d382f6472f8568a18b2f057a1460297556", "51ed8885530449df0c4169fe80ba3a9f217f0f09ae701b5fc378f3c84f8a0998"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd036413c", "2f8bde4d1a07209355b4a7250a5c5128e88b84bddc619ab7cba8d569b240efe4", "2753ddd9c91a1c292b24562259363bd90877d8e454f297bf235782c459539959"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd036413d", "e493dbf1c10d80f3581e4904930b1404cc6c13900ee0758474fa94abe8c4cd13", "ae1266c15f2baa48a9bd1df6715aebb7269851cc404201bf30168422b88c630d"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd036413e", "f9308a019258c31049344f85f89d5229b531c845836f99b08601f113bce036f9", "c77084f09cd217ebf01cc819d5c80ca99aff5666cb3ddce4934602897b4715bd"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd036413f", "c6047f9441ed7d6d3045406e95c07cd85c778e4b8cef3ca7abac09b95c709ee5", "e51e970159c23cc65c3a7be6b99315110809cd9acd992f1edc9bce55af301705"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364140", "79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798", "b7c52588d95c3b9aa25b0403f1eef75702e84bb7597aabe663b82f6f04ef2777"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141", null, null},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364142", "79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798", "483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364143", "c6047f9441ed7d6d3045406e95c07cd85c778e4b8cef3ca7abac09b95c709ee5", "1ae168fea63dc339a3c58419466ceaeef7f632653266d0e1236431a950cfe52a"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364144", "f9308a019258c31049344f85f89d5229b531c845836f99b08601f113bce036f9", "388f7b0f632de8140fe337e62a37f3566500a99934c2231b6cb9fd7584b8e672"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364145", "e493dbf1c10d80f3581e4904930b1404cc6c13900ee0758474fa94abe8c4cd13", "51ed993ea0d455b75642e2098ea51448d967ae33bfbdfe40cfe97bdc47739922"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364146", "2f8bde4d1a07209355b4a7250a5c5128e88b84bddc619ab7cba8d569b240efe4", "d8ac222636e5e3d6d4dba9dda6c9c426f788271bab0d6840dca87d3aa6ac62d6"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364147", "fff97bd5755eeea420453a14355235d382f6472f8568a18b2f057a1460297556", "ae12777aacfbb620f3be96017f45c560de80f0f6518fe4a03c870c36b075f297"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364148", "5cbdf0646e5db4eaa398f365f2ea7a0e3d419b7e0330e39ce92bddedcac4f9bc", "6aebca40ba255960a3178d6d861a54dba813d0b813fde7b5a5082628087264da"},
			{"fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364149", "2f01e5e15cca351daff3843fb70f3c2f0a1bdd05e5af888a67784ef3e10a2a01", "5c4da8a741539949293d082a132d13b4c2e213d6ba5b7617b5da2cb76cbde904"},
			{"fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8", "d8975566d098bc58d56ac778eb2445a8bbbbfea11a70e0aed7258d899e1c0613", "94e691957255ff0c5f2d79bbcdeaea3c25df63c268a10af2e43e7b90e57e9252"},
			{"fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff9", "0527cf8a882b7a8335cc794152791aac0a2e3580f2375c4eabfa99861c8a90c1", "ae4c367f859c2fe818492912a3237e0c33a5aba4e30874287281c44c5c9f032a"},
			{"fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffa", "1ff4ddb68af6aeb749b8107b38d76108c0c4562f8efe6087e04575305516caa5", "991a7cca5073b7f44bcc834d6dfe90eb155ee54d774122e3b9387cd26b7a7156"},
			{"fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffb", "64f6309019333daf98303b4630efcc46def0c895d16f830d140c833947f00e1b", "50e58e52650ed0dc8bb1d178a4b14187b4fd9edc98a29327b5f2a7b4e6108115"},
			{"fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffc", "2cb62ea7395b82df182570f2adc7326c7cc7fe8d1db31a2071c5e831e3b0df74", "a0fade1641324fc1e4de38568ce5124d9e161a82fdbc40f2df4aade4d58dcf56"},
			{"fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd", "3fbeac6ef73d496e597dab76c75848e2633cfe3a8492b360d7a21b223afce95d", "1a9128bff344ca910e8334027de4308b3acabcfe6b9799c7a0bfde45a6facf67"},
			{"fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe", "e0a65740c1c5148111a8305938c56322fbf345a18cb90cf48a788961c6b3ff91", "8aaf0580fd9e8be571e97bef4a067fa7911da0ff0e1a5f307fea63e3ecd903af"},
			{"ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "9166c289b9f905e55f9e3df9f69d7f356b4a22095f894f4715714aa4b56606af", "f181eb966be4acb5cff9e16b66d809be94e214f06c93fd091099af98499255e7"},
			// Random multiples
			{"45528a55356f7c32ca753f1e58627bc33863670a1072d9c8dd0663eb5691d87f", "86f12a5bda1db7f296b4f1545e417d2dabb28c1e7c96475b97d5aeb9fcad71a5", "2fe6419626eb949db7a6439c6d54a78d42aa596ed74f117e51a6e1cd7af78c1a"},
			{"11d6dd13d560e703c7f0189140df2f692b603ef57a5e10e29c3e163acd1e8ff8", "e222674c42d1b962e51f11c4138a6a75e5ed73d16921c7d381dca0692b2c172e", "90747484c1616b024139138492c716092e41f4a631015196022330b3c5bff7cb"},
			{"e1e1dd6fba9d293b4f5f46be5a3f05a7bce9cdbd8993bc0282dcf6b975d285f1", "75608fb5896949f01c5ab5c655c55b91e56aa40deb0b66dea9bffac718a8be42", "5c75d5ca6abc9f8171e6bbb4ef9b1be632a2fb25bab0e345548505ecc1f3a45e"},
			{"3cbd5de8e80196f3f5db4d925a29638e4bfe4a9a848a9424bad5c46136837c1f", "a964798ae92f981676341bed95e95646fd357de2929666fa967f605f3fb1c739", "06bcbb55bf3c38660c778029a64ceca44bd7af9ae346164fa7ba5a99a292a899"},
			{"efa4125849134857fbc1a97675a43b5de6c1d268e2ca7ab1e179db25f13de00c", "4e3e7a7387c7d8ab2f90df43191f2cf56fb4f09bf1b1ed66501e4e231e4d65a6", "855323dd2b0bded7e96e641a97b5e4d1bcc97ea2a4453bec003208c18896e9c1"},
			{"b177e15f7cf844c240a7749f332df50a692a372d6affd786629686540c8e73f2", "79b63c33938948a2bebf8670ed0a21af7395584d644b0c7f2667a9672030b199", "089ffb15f84a98be1bb2fabd4cfc9fc2b748ec158974d1b5a9aa5191ecb7e7d4"},
			{"9806e0e601fde9777cfba9be56fe90453023e6b08f5be6cb18555156de3ab5c3", "2f413ca9cb2a6e15120b1fcb4f57e907ad3d54e5034a7d7d7efadf59ef5bf50f", "f83c779a884e36d307e6b588b53f62a6407d6f8815708d831ae8102ee26940f3"},
			{"b26c14ecabf65120bc8786a981edad985e8c1d42b146d7f66a8759919b02ae11", "d5e97efbb234e24b4cd39d59062e925a355901ee4d89899c638b4cd9b16cf829", "af21fc31e958c61dc1c6dda55e99444bf04e55940fa71b891540aa4ac94da3bf"},
			{"f56a9e89525ff39cfe2d18bada4e050d0875bf2a2c97237bac9b0f31d191e250", "95cece9600b90d3352e868a5b497f9d134c05a882cb1308fe583061dd6c59f24", "fc8b7a4555770ff9e05a96a3828141ae69b9b89a8e06dea1d5b6da42c95513b8"},
			{"6c2ce63745f8d521bf120f360fbcdc56c8411e177ab6637389e18d289257d435", "222d89ad4cb409ad7ada41ab280d8d2bcc723de7b8521cafa03ad8c839d6cbd1", "c26957d3094f04b394a45e3dbcd11b631bd4f473d293d85e7e92457ab2f23737"},
			{"9ce2b3d4ecbb3ce8cbfebc365d5b0ee3d1dcde114f0a7944cf357a770d27a5e5", "0f92952b176bb2b66b7251016b6ef3a10979a7dd20e3de017498f339b7d47740", "ce2e23afddb77590d6f2b6da13ad70fa212b89a885ff44545cf7eade9dbd1627"},
			{"8e237b486de3bb3156bb7f1493f31e5709449113779b2e70c061ba6ec748de5b", "62d8f6d03a41eb698d845b27e1cfd862a6d4e178b55db4979ac0b01a84599e5c", "8b1926377d460e2a77f0687741dcf5e7b720a24e8189fe2d1706055fa0727b49"},
			{"ba83b8bb8081b74de6b7a2f24d9aa80beb36a8f3670f62715846c4b66b6bf55d", "8a841f13c93473387b2d6db8f43c82ec80a561dff790d0c02f54ac6e8babe596", "981b5ea656254e84efdb72552164deae0e6c452ed2a462763dda1dfb7431775c"},
			{"1bebe58f680ef4f9b05904ad0a06b28da2796f5fcad0ef7a6156dc5b981c89ea", "04f307974e735568bf7ed84d6d9e47c8cbf78362347216103d599233606e02ea", "ef06a534b729ab791c13f93567dad39bc6a85ebac3e607ad1045c4a2ec45abae"},
			{"fa4bb94494e7dd1fd59ea728946e17434eaa6d5e0b9bae191581c55e6a04addf", "a705fdbbd5f8c8f64bed6f13d667357ed200ef4738faaa9d253a3687c0f3e194", "14a40ddd8053bef96529e8a6e4d96579af294fec43986ad483302250a91cb52e"},
			{"f30c65dfe796f9f5e72ffbf00a5693f69bfa56d4a9aba2f12a7f19000e7d2884", "1b24b78e17d1ccc8be2b901dbf3c9f36c3d0be35a30e29b9425c17d06579c3ae", "5ee17b92e7c33f4b4376316ad409cfa729872f06aa70cacb3214b054fc17005c"},
			{"df3a3dc1913ceb70718fa0a07118454b8d5ce9894e0aef5e40df7eb55e32d9df", "da4316fb9d85bf0485e0ce6380b99210f8bea0caf641684f98ef586bdd1295e5", "07860148aabdb47c310dedae95116e40858c1a0e2dd296d821618b3d822ed9ee"},
			{"771057bba5b9c168f34204651c9bd9948ca7f819b717f213c020176bc47c01fe", "8bc8442e4b445df8ae1a723104047b5b599a495a006521041d49819d9bbc4dc3", "cd655aac8d8e1c69ffe8142d12b6445da7ce239f115c63db3caa688c68143c99"},
			{"815a1ef6bb5458ad1567571aacf0b7da2fb5d4307cb52059b1211f3a40469e0e", "c7eb10c596cd9ad02970170b8ca424c310a123589468e615ab1ccb08c0889f67", "26c39ad0442d3b294345eaeda3316dc8bbe5fcb91677068ca6bf4e78d07cf2e6"},
			{"866924a28917a33fe144db60b4d7a27f1dc8b0d26d63372f76e629642245a76b", "6058612c8520cb807e753132e14d497bfd9ec74b172356e5ddc78da1d2aa97a8", "4ebf15d5285a619c6987d47079af89e544c7987795c427939bc5728663295fc4"},
			{"fae4afecd392e1f88eeb28b8ff0c25569530b8dc7141b8061397aed9a49cf9f2", "f46e371ab5fd98a0ed4b15c92cfadecb0787319326a7e561ddd8a6c6080f8642", "1d2b15146f7a7171a1a2d66fb55f50ad970122e7f87f3754a6016d0a52818de0"},
			{"3bfd4640f65ae89e5e46b027d969abcf046af5bde20f8e923787de6473811965", "4bf1e32c1b69d7e0b95726eb8dcf293fb9afb5f4cba7c00f95e2ccd4ee0fd14d", "725352959be783b2e64055f76ff1eace840fa43fa8f72df4db0889ff3e997ddc"},
			{"3dd6e832384e4c1283debf1cd9af94484a6a4458d7fa88fcb853317e923bfee1", "488cecbfed358ede1ce51fcb9f2b34c23f7feb67a800063ab8212871e1941d13", "fbe324ab8fa3c3a3b6d71281fe4555ac80689ea29453f43445d8b74257039f13"},
			{"ca0ccb9483483bd0601220b8a1e5a2f93acad18c794c9eb14c8ab7d8a4001e2e", "8013d6bc2f12a279617c93de87fe85aee7a9f82bd01274301da32126a3d5e8ac", "bed9ed257d0cc0b956f192bcc1f00190a7d0293c222648eb600d28fb3f22cf66"},
			{"9c6236188056ac2cb99cc60fd38ced951590e004282480883bc88b74a1eab3a7", "8333198b3b42e44586a25f0cf5371133220703b44c9b7612fd83abbbba4f78d8", "4c633a0ca354671cf632ed36f749c66bf9d979414ed52dcf04270a0a60224c7a"},
			{"f563bba48829799d3d60e66ac9c0537d1dbdd32a1c2d893153a8384feb0062d8", "2ee54fbb45b4b7a0bf8c5ba332b898d7aedccce1b3e4cda56872ff77fb1f7a2c", "7d5b84b9c5164df9979c5dbfd6fc31d112939f196a9a437329a36742d5e51611"},
			{"406dc83d14de5d2d272c480c42f5988ad6154adcdba66f1d03e7aa36693ff7d3", "d706b8d7ed2e83fc7aa5284de94edbdcaf9fa0d2e1f1c56341f1fbcc570b5612", "0efd57aa3345e14f4f7f290c0fc924fa51fa2a069c8ec961999a2819c0eccafc"},
			{"b9199737b6e6c3126bb68d37e938ecce04f6ecbeeda1e9cbf10aa6138b5c842f", "7943a0c63e51d571184b2469bb371f4d5a2a66bb32e7bb44eb4974882bad8b76", "9387384d13291f8df67ec5ec862f1f5372503771811d5daa4efe190d75dd1a39"},
			{"4263b3e7a22922c05ffce2e922ceebc583b7e5c537c8a4f3572da96bba8813cb", "f1335e670e5cb076ba973c92b24c87bda85b962416539b2111a4317b4be9ae44", "106c70ee074a552ff61d885616003a1139d86a2845464f59c742677464da2110"},
			{"f750484a92e6ed351c6d5cc9738085f62c5389c11e7c6bdfa292efaf20d0ac1c", "3ca82e74725ed3a9d359ae58d094d2e5c327b3f7b1671452c3517842538ff204", "9eba56838328ad2fd2091050c8db174b7952b28ccf9e322e2a006958da3fb625"},
			{"6e139e20daf5e0f98d0f21b7648016579809019f9022da7334edd864e7916b3d", "46f45c0139653c58f80377522f5e43e53e16045a88053ac84a00fd5ea6bfee7d", "743ba29a19e2f118037977b8a614d8dc2427d538c7198632aed237d48e6fe9dd"},
			{"1b4b9e14eab0f76f57b01e1155548ff0ca5ab75c7b28b0ead962662d875c6b74", "a173d134375b449eed2d28b941ff935ec3fcf66255bd6fb10cfd52c219e48812", "133b122da45cc3ddc3e2b5c02e9d86f0aaf4fbe564ef5fcba59ce87858569eaf"},
			{"098ff774af194bedf730a2e3e42d6ba9dcada504b1610133296cfa4def20ce53", "3ba9c946d318b32252b212337e7deaf1127fce02210cd2e4487c28ce8e04307b", "4c5d9a560e8666ac90bcf88fd39a0beb4d302d9b7ad0d5683a2f8d32acd7c988"},
			{"02b36516f5976cf6c3cbd5d5663db4e1c93dd67383ee79627be8a1f2a93ed0ac", "fd01fa210db77f9126f7559b5e0af8927d4a4c45ae5233baf025e5596aa13c58", "b95c7d1519cd85e075ae293a0272a7aaf2cb97f29b64a351a2c512cff7df6b49"},
			{"204ff2cc9f835a21b2fa49d7b3b712b6acb2e3a98e1ef0326f7965dde8e1133e", "9edd38343d35b22c057d37199fbbc530b96e630ba7b4c9a7c5268cc38d626787", "2ccdaaeae19f3a0a5fa1b2c3c03984dfad390262dde254832a1bfb5e4860dbe7"},
			{"c1d10168789cb7eaedb80be73491b572e8866f9c6a8be13a219611d203e96db4", "aed9d3d5b9dc1830a567a837f4acc1a04eb5530ba7035a72c013f54e292834a4", "b967438fcef87d8be5eebbdb336f7606e852e7229dc22db14ba2b17fd7c9553b"},
			{"c279603b48cefc3bdfaaf80f5548b11612c4bcb0746b19915977bb9b21c5f718", "acd33dfb09d55be1a21729408df603253b55087555e4012d677756a02278906b", "660650032dbcaba6cac73027ff78277287a7fbcc6cb4b4a769f4fdd6ec5f5c11"},
			{"7bcba36d1148c7a5ac938ca14c19a947e69f230577c182e243eea7539e5c544b", "7b5d4b743d6fa328dbe32d49c276b123c04887aeaec01f117582e846f6f2255a", "35c32c24b0499a3968cbd609a32eb3627ea99a5798bf50be8b28608bdbe5facb"},
			{"59b5e4509b28e1ea788c777c73337e4dc4f465c8772dad204c8ee5fafe2d4645", "51471c106ee9bc9e4d46acf6409fa3c13787835080b2fe921bb2ccf9699636a9", "8272b678eb7e805a0725bc709e2bb2aea7f10f3a53dae3512d6dc8eebf1d137b"},
		};
		for (String[] cs : cases) {
			
			int pOff = rand.nextInt(5) * 8;
			int nOff = pOff + 24 + rand.nextInt(5) * 8;
			int tempOff = nOff + 8 + rand.nextInt(5) * 8;
			int[] arr = new int[tempOff + 552];
			for (int i = 0; i < arr.length; i++)
				arr[i] = rand.nextInt();
			
			System.arraycopy(CurvePointMath.getBasePoint(), 0, arr, pOff, 24);
			toInt256(cs[0], arr, nOff);
			
			CurvePointMath.multiply(arr, pOff, nOff, tempOff);
			CurvePointMath.normalize(arr, pOff, tempOff);
			if (cs[1] == null && cs[2] == null)
				assertArrayEquals(POINT_ZERO, Arrays.copyOfRange(arr, pOff + 0, pOff + 24));
			else {
				assertArrayEquals(toInt256(cs[1]), Arrays.copyOfRange(arr, pOff + 0, pOff + 8));
				assertArrayEquals(toInt256(cs[2]), Arrays.copyOfRange(arr, pOff + 8, pOff + 16));
				assertArrayEquals(INT256_ONE, Arrays.copyOfRange(arr, pOff + 16, pOff + 24));
			}
		}
	}
	
	
	/*---- Helper definitions ----*/
	
	private static int[] toInt256(String s) {
		int[] result = new int[8];
		toInt256(s, result, 0);
		return result;
	}
	
	
	private static void toInt256(String s, int[] arr, int off) {
		for (int i = 0; i < 8; i++)
			arr[off + i] = (int)Long.parseLong(s.substring((7 - i) * 8, (8 - i) * 8), 16);
	}
	
	
	private static final int[] INT256_ONE = {1, 0, 0, 0, 0, 0, 0, 0};
	private static final int[] POINT_ZERO = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	
	private static Random rand = new Random();
	
}
