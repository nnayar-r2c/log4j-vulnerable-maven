import os
import jwt
import jwt as lsadlsaldsa
key = "secret"

# ruleid: python-pyjwt-hardcoded-secret
jwt.encode({"some": "payload"}, key, algorithm="HS256")
# ruleid: python-pyjwt-hardcoded-secret
jwt.encode({"some": "payload"}, "123", algorithm="HS256")
# ruleid: python-pyjwt-hardcoded-secret
lsadlsaldsa.encode({"some": "payload"}, "123", algorithm="HS256")
# ok: python-pyjwt-hardcoded-secret
jwt.encode({"some": "payload"}, uh, algorithm="HS256")

# ok: python-pyjwt-hardcoded-secret
jwt.encode({"some": "payload"}, os.getenv('secret'), algorithm="HS256")

# ok: python-pyjwt-hardcoded-secret
jwt.encode({"some": "payload"}, os.env['secret'], algorithm="HS256")
